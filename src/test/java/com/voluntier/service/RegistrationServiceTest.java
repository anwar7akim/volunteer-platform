package com.voluntier.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** FR-003, FR-004, NFR-003. */
class RegistrationServiceTest {
    private final Fixture f = new Fixture();

    @Test void TC013_registerWhenSpaceAvailable() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(10), 5);
        f.registrations.register(f.volunteer, id);
        assertEquals(1, f.registrations.upcomingFor(f.volunteer).size());
        assertEquals(4, f.registrations.remainingSpaces(id));
    }

    @Test void TC014_duplicateRegistrationIsRejected() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(10), 5);
        f.registrations.register(f.volunteer, id);
        ValidationException e = assertThrows(ValidationException.class, () -> f.registrations.register(f.volunteer, id));
        assertEquals("You are already registered for this opportunity.", e.getMessage());
        assertEquals(1, f.registrations.upcomingFor(f.volunteer).size());
    }

    @Test void TC015_fullOpportunityIsRejected() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(10), 1);
        f.registrations.register(f.volunteer2, id);
        ValidationException e = assertThrows(ValidationException.class, () -> f.registrations.register(f.volunteer, id));
        assertEquals("This opportunity is full.", e.getMessage());
        assertTrue(f.registrations.upcomingFor(f.volunteer).isEmpty());
    }

    @Test void TC016_lastSpaceGoesToFirstVolunteerOnly() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(10), 1);
        f.registrations.register(f.volunteer, id);
        assertEquals(0, f.registrations.remainingSpaces(id));
        assertThrows(ValidationException.class, () -> f.registrations.register(f.volunteer2, id));
    }

    @Test void TC017_withdrawMoreThanThreeDaysBefore() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(7), 5);
        f.registrations.register(f.volunteer, id);
        f.registrations.withdraw(f.volunteer, id);
        assertTrue(f.registrations.upcomingFor(f.volunteer).isEmpty());
        assertEquals(5, f.registrations.remainingSpaces(id));
    }

    @Test void TC018_withdrawWithinThreeDaysIsBlocked() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(2), 5);
        f.registrations.register(f.volunteer, id);
        assertThrows(ValidationException.class, () -> f.registrations.withdraw(f.volunteer, id));
        assertEquals(1, f.registrations.upcomingFor(f.volunteer).size());
    }

    @Test void TC019_withdrawExactlyThreeDaysIsBlocked() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(3), 5);
        f.registrations.register(f.volunteer, id);
        assertThrows(ValidationException.class, () -> f.registrations.withdraw(f.volunteer, id));
    }

    @Test void TC006c_coordinatorCannotRegister() {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(10), 5);
        assertThrows(ValidationException.class, () -> f.registrations.register(f.coordinator, id));
    }

    @Test void TC038_concurrentRegistrationsCreateNoDuplicatesAndRespectCapacity() throws Exception {
        String id = f.post("Food Bank", "Food", Fixture.NOW.plusDays(10), 3);
        var pool = java.util.concurrent.Executors.newFixedThreadPool(8);
        var tasks = new java.util.ArrayList<java.util.concurrent.Callable<Boolean>>();
        for (int i = 0; i < 20; i++) {
            tasks.add(() -> {
                try { f.registrations.register(f.volunteer, id); return true; }
                catch (ValidationException e) { return false; }
            });
        }
        long ok = pool.invokeAll(tasks).stream().filter(t -> {
            try { return t.get(); } catch (Exception e) { return false; }
        }).count();
        pool.shutdown();
        assertEquals(1, ok);
        assertEquals(2, f.registrations.remainingSpaces(id));
    }
}
