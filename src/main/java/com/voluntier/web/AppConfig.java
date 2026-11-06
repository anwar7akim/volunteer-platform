package com.voluntier.web;

import com.voluntier.model.Role;
import com.voluntier.service.AuthService;
import com.voluntier.service.OpportunityService;
import com.voluntier.service.RegistrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;

/** Wires the plain service classes as Spring beans and loads demo data. */
@Configuration
public class AppConfig {
    @Bean Clock clock() { return Clock.systemDefaultZone(); }

    @Bean AuthService authService() { return new AuthService(); }

    @Bean OpportunityService opportunityService(AuthService auth, Clock clock) {
        return new OpportunityService(auth, clock);
    }

    @Bean RegistrationService registrationService(AuthService auth, OpportunityService opps, Clock clock) {
        return new RegistrationService(auth, opps, clock);
    }

    @Bean CommandLineRunner demoData(AuthService auth, OpportunityService opps) {
        return args -> {
            auth.addUser("u1", "volunteer@voluntier.test", "password", Role.VOLUNTEER);
            auth.addUser("u2", "volunteer2@voluntier.test", "password", Role.VOLUNTEER);
            var coordinator = auth.addUser("u3", "coordinator@voluntier.test", "password", Role.COORDINATOR);
            auth.addUser("u4", "admin@voluntier.test", "password", Role.ADMINISTRATOR);
            LocalDateTime now = LocalDateTime.now();
            opps.create(coordinator, "Food Bank Sorting", "Sort and pack donations.", "Food", now.plusDays(10), "Ottawa Food Bank", 5);
            opps.create(coordinator, "Park Cleanup", "Pick up litter along the river path.", "Environment", now.plusDays(5), "Rideau River Park", 8);
            opps.create(coordinator, "Homework Help", "Tutor students after school.", "Education", now.plusDays(2), "Community Centre", 1);
        };
    }
}
