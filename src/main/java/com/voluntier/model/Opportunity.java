package com.voluntier.model;

import java.time.LocalDateTime;

public record Opportunity(String id, String title, String description, String category,
                          LocalDateTime start, String location, int capacity, Status status) {

    public Opportunity withStatus(Status newStatus) {
        return new Opportunity(id, title, description, category, start, location, capacity, newStatus);
    }

    public Opportunity with(String newTitle, String newDescription, String newCategory,
                            LocalDateTime newStart, String newLocation, int newCapacity) {
        return new Opportunity(id, newTitle, newDescription, newCategory, newStart, newLocation, newCapacity, status);
    }
}
