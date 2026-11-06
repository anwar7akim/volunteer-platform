package com.voluntier.service;

import com.voluntier.model.Feature;
import com.voluntier.model.Role;
import com.voluntier.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** FR-001, NFR-004, NFR-006. */
class AuthServiceTest {
    private final Fixture f = new Fixture();

    @Test void TC001_validVolunteerLogin() {
        User u = f.auth.login("vol@example.com", "pass123");
        assertEquals(Role.VOLUNTEER, u.role());
        assertTrue(f.auth.canAccess(u, Feature.REGISTER));
        assertFalse(f.auth.canAccess(u, Feature.MANAGE_OPPORTUNITIES));
    }

    @Test void TC002_validCoordinatorLogin() {
        User u = f.auth.login("coord@example.com", "pass123");
        assertEquals(Role.COORDINATOR, u.role());
        assertTrue(f.auth.canAccess(u, Feature.MANAGE_OPPORTUNITIES));
    }

    @Test void TC003_validAdministratorLogin() {
        User u = f.auth.login("admin@example.com", "pass123");
        assertEquals(Role.ADMINISTRATOR, u.role());
        assertTrue(f.auth.canAccess(u, Feature.MANAGE_COORDINATOR_ACCESS));
        assertTrue(f.auth.canAccess(u, Feature.VIEW_PROGRAM_SUMMARIES));
    }

    @Test void TC004_wrongPasswordIsRejected() {
        ValidationException e = assertThrows(ValidationException.class,
            () -> f.auth.login("vol@example.com", "wrong"));
        assertEquals("Email or password is not correct.", e.getMessage());
    }

    @Test void TC005_emptyFieldsAreRejected() {
        assertThrows(ValidationException.class, () -> f.auth.login("", ""));
        assertThrows(ValidationException.class, () -> f.auth.login(null, null));
    }

    @Test void TC006_volunteerCannotUseCoordinatorFeatures() {
        assertThrows(ValidationException.class, () -> f.auth.requireAccess(f.volunteer, Feature.MANAGE_OPPORTUNITIES));
        assertThrows(ValidationException.class, () -> f.auth.requireAccess(f.volunteer, Feature.RECORD_ATTENDANCE));
    }

    @Test void TC007_coordinatorCannotUseAdministratorFeatures() {
        assertThrows(ValidationException.class, () -> f.auth.requireAccess(f.coordinator, Feature.MANAGE_COORDINATOR_ACCESS));
        assertThrows(ValidationException.class, () -> f.auth.requireAccess(f.coordinator, Feature.VIEW_PROGRAM_SUMMARIES));
    }
}
