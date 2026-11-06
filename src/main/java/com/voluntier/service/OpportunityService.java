package com.voluntier.service;

import com.voluntier.model.Feature;
import com.voluntier.model.Opportunity;
import com.voluntier.model.Status;
import com.voluntier.model.User;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Create, edit, cancel, browse, search and filter opportunities (FR-002, FR-006). */
public class OpportunityService {
    private final Map<String, Opportunity> store = new LinkedHashMap<>();
    private final AuthService auth;
    private final Clock clock;

    public OpportunityService(AuthService auth, Clock clock) {
        this.auth = auth;
        this.clock = clock;
    }

    public Opportunity create(User actor, String title, String description, String category,
                              LocalDateTime start, String location, int capacity) {
        auth.requireAccess(actor, Feature.MANAGE_OPPORTUNITIES);
        validate(title, description, start, location, capacity);
        Opportunity o = new Opportunity(UUID.randomUUID().toString(), title.trim(), description.trim(),
            category, start, location.trim(), capacity, Status.OPEN);
        store.put(o.id(), o);
        return o;
    }

    public Opportunity edit(User actor, String id, String title, String description, String category,
                            LocalDateTime start, String location, int capacity) {
        auth.requireAccess(actor, Feature.MANAGE_OPPORTUNITIES);
        validate(title, description, start, location, capacity);
        Opportunity updated = get(id).with(title.trim(), description.trim(), category, start, location.trim(), capacity);
        store.put(id, updated);
        return updated;
    }

    public Opportunity cancel(User actor, String id) {
        auth.requireAccess(actor, Feature.MANAGE_OPPORTUNITIES);
        Opportunity cancelled = get(id).withStatus(Status.CANCELLED);
        store.put(id, cancelled);
        return cancelled;
    }

    public Opportunity get(String id) {
        Opportunity o = store.get(id);
        if (o == null) throw new ValidationException("Opportunity not found.");
        return o;
    }

    /** Open opportunities only. A null or blank search or category means no filter. */
    public List<Opportunity> browse(String search, String category) {
        List<Opportunity> result = new ArrayList<>();
        for (Opportunity o : store.values()) {
            if (o.status() != Status.OPEN) continue;
            if (search != null && !search.isBlank()) {
                String q = search.trim().toLowerCase();
                if (!o.title().toLowerCase().contains(q) && !o.description().toLowerCase().contains(q)
                    && !o.location().toLowerCase().contains(q)) continue;
            }
            if (category != null && !category.isBlank() && !category.equalsIgnoreCase(o.category())) continue;
            result.add(o);
        }
        return result;
    }

    private void validate(String title, String description, LocalDateTime start, String location, int capacity) {
        if (title == null || title.isBlank()) throw new ValidationException("Title is required.");
        if (description == null || description.isBlank()) throw new ValidationException("Description is required.");
        if (location == null || location.isBlank()) throw new ValidationException("Location is required.");
        if (start == null) throw new ValidationException("Date and time are required.");
        if (!start.isAfter(LocalDateTime.now(clock))) throw new ValidationException("Date must be in the future.");
        if (capacity < 1) throw new ValidationException("Capacity must be at least 1.");
    }
}
