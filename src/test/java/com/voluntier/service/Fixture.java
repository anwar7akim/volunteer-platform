package com.voluntier.service;

import com.voluntier.model.Role;
import com.voluntier.model.User;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/** Shared test setup. The clock is fixed at 2026-11-01 10:00. */
class Fixture {
    static final LocalDateTime NOW = LocalDateTime.of(2026, 11, 1, 10, 0);
    final Clock clock = Clock.fixed(NOW.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    final AuthService auth = new AuthService();
    final OpportunityService opportunities = new OpportunityService(auth, clock);
    final RegistrationService registrations = new RegistrationService(auth, opportunities, clock);
    final User volunteer = auth.addUser("u1", "vol@example.com", "pass123", Role.VOLUNTEER);
    final User volunteer2 = auth.addUser("u2", "vol2@example.com", "pass123", Role.VOLUNTEER);
    final User coordinator = auth.addUser("u3", "coord@example.com", "pass123", Role.COORDINATOR);
    final User admin = auth.addUser("u4", "admin@example.com", "pass123", Role.ADMINISTRATOR);

    String post(String title, String category, LocalDateTime start, int capacity) {
        return opportunities.create(coordinator, title, "Help out", category, start, "Ottawa", capacity).id();
    }
}
