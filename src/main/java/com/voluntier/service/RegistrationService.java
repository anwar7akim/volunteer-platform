package com.voluntier.service;

import com.voluntier.model.Feature;
import com.voluntier.model.Opportunity;
import com.voluntier.model.Registration;
import com.voluntier.model.Status;
import com.voluntier.model.User;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Register and withdraw (FR-003, FR-004, NFR-003). */
public class RegistrationService {
    static final Duration WITHDRAW_CUTOFF = Duration.ofDays(3);

    private final Set<Registration> registrations = new LinkedHashSet<>();
    private final AuthService auth;
    private final OpportunityService opportunities;
    private final Clock clock;

    public RegistrationService(AuthService auth, OpportunityService opportunities, Clock clock) {
        this.auth = auth;
        this.opportunities = opportunities;
        this.clock = clock;
    }

    public synchronized Registration register(User volunteer, String opportunityId) {
        auth.requireAccess(volunteer, Feature.REGISTER);
        Opportunity o = opportunities.get(opportunityId);
        if (o.status() != Status.OPEN) throw new ValidationException("This opportunity is not accepting registrations.");
        Registration r = new Registration(volunteer.id(), opportunityId);
        if (registrations.contains(r)) throw new ValidationException("You are already registered for this opportunity.");
        if (remainingSpaces(opportunityId) <= 0) throw new ValidationException("This opportunity is full.");
        registrations.add(r);
        return r;
    }

    /** The event must start more than three days from now. Exactly three days is blocked. */
    public synchronized void withdraw(User volunteer, String opportunityId) {
        auth.requireAccess(volunteer, Feature.WITHDRAW);
        Registration r = new Registration(volunteer.id(), opportunityId);
        if (!registrations.contains(r)) throw new ValidationException("You are not registered for this opportunity.");
        LocalDateTime cutoff = opportunities.get(opportunityId).start().minus(WITHDRAW_CUTOFF);
        if (!LocalDateTime.now(clock).isBefore(cutoff)) {
            throw new ValidationException("You cannot withdraw within three days of the event.");
        }
        registrations.remove(r);
    }

    public synchronized int remainingSpaces(String opportunityId) {
        long taken = registrations.stream().filter(r -> r.opportunityId().equals(opportunityId)).count();
        return opportunities.get(opportunityId).capacity() - (int) taken;
    }

    public synchronized List<Registration> upcomingFor(User volunteer) {
        return registrations.stream().filter(r -> r.userId().equals(volunteer.id())).toList();
    }
}
