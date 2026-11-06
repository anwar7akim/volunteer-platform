package com.voluntier.service;

import com.voluntier.model.Opportunity;
import com.voluntier.model.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** FR-002, FR-006, NFR-006. */
class OpportunityServiceTest {
    private final Fixture f = new Fixture();
    private final LocalDateTime future = Fixture.NOW.plusDays(10);

    @Test void TC008_browseListsOpenOpportunitiesWithDetails() {
        f.post("Food Bank Sorting", "Food", future, 5);
        f.post("Park Cleanup", "Environment", future, 8);
        f.post("Tutoring", "Education", future, 3);
        List<Opportunity> list = f.opportunities.browse(null, null);
        assertEquals(3, list.size());
        Opportunity o = list.get(0);
        assertNotNull(o.start());
        assertNotNull(o.location());
        assertNotNull(o.description());
        assertEquals(5, o.capacity());
    }

    @Test void TC009_searchMatchesTerm() {
        f.post("Food Bank Sorting", "Food", future, 5);
        f.post("Park Cleanup", "Environment", future, 8);
        List<Opportunity> list = f.opportunities.browse("food bank", null);
        assertEquals(1, list.size());
        assertEquals("Food Bank Sorting", list.get(0).title());
    }

    @Test void TC010_searchWithNoMatchReturnsEmptyList() {
        f.post("Food Bank Sorting", "Food", future, 5);
        assertTrue(f.opportunities.browse("zzzzqq", null).isEmpty());
    }

    @Test void TC011_filterByCategory() {
        f.post("Food Bank Sorting", "Food", future, 5);
        f.post("Park Cleanup", "Environment", future, 8);
        List<Opportunity> list = f.opportunities.browse(null, "Environment");
        assertEquals(1, list.size());
        assertEquals("Park Cleanup", list.get(0).title());
    }

    @Test void TC012_detailsShowAllFields() {
        String id = f.post("Food Bank Sorting", "Food", future, 5);
        Opportunity o = f.opportunities.get(id);
        assertEquals("Food Bank Sorting", o.title());
        assertEquals("Help out", o.description());
        assertEquals(future, o.start());
        assertEquals("Ottawa", o.location());
        assertEquals(5, o.capacity());
    }

    @Test void TC023_createWithValidInputsAppearsInList() {
        String id = f.post("Food Bank Sorting", "Food", future, 10);
        assertEquals(id, f.opportunities.browse(null, null).get(0).id());
    }

    @Test void TC024_missingTitleIsRejectedAndNothingSaved() {
        assertThrows(ValidationException.class, () ->
            f.opportunities.create(f.coordinator, " ", "d", "Food", future, "Ottawa", 5));
        assertTrue(f.opportunities.browse(null, null).isEmpty());
    }

    @Test void TC025_pastDateOrBadCapacityIsRejected() {
        assertThrows(ValidationException.class, () ->
            f.opportunities.create(f.coordinator, "t", "d", "Food", Fixture.NOW.minusDays(1), "Ottawa", 5));
        assertThrows(ValidationException.class, () ->
            f.opportunities.create(f.coordinator, "t", "d", "Food", future, "Ottawa", 0));
        assertThrows(ValidationException.class, () ->
            f.opportunities.create(f.coordinator, "t", "d", "Food", future, "Ottawa", -4));
        assertTrue(f.opportunities.browse(null, null).isEmpty());
    }

    @Test void TC026_editSavesChange() {
        String id = f.post("Food Bank Sorting", "Food", future, 5);
        f.opportunities.edit(f.coordinator, id, "Food Bank Sorting", "New description", "Food", future, "Ottawa", 5);
        assertEquals("New description", f.opportunities.get(id).description());
    }

    @Test void TC027a_cancelRemovesOpportunityFromBrowseList() {
        String id = f.post("Food Bank Sorting", "Food", future, 5);
        f.opportunities.cancel(f.coordinator, id);
        assertEquals(Status.CANCELLED, f.opportunities.get(id).status());
        assertTrue(f.opportunities.browse(null, null).isEmpty());
    }

    @Test void TC006b_volunteerCannotCreateOpportunity() {
        assertThrows(ValidationException.class, () ->
            f.opportunities.create(f.volunteer, "t", "d", "Food", future, "Ottawa", 5));
    }
}
