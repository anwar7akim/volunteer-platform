package com.voluntier.model;

public record User(String id, String email, String passwordHash, Role role) { }
