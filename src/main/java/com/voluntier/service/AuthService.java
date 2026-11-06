package com.voluntier.service;

import com.voluntier.model.Feature;
import com.voluntier.model.Role;
import com.voluntier.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.Set;

/** Role-based login and access checks (FR-001, NFR-004). */
public class AuthService {
    private static final Map<Role, Set<Feature>> ACCESS = Map.of(
        Role.VOLUNTEER, Set.of(Feature.BROWSE_OPPORTUNITIES, Feature.REGISTER, Feature.WITHDRAW,
                               Feature.VIEW_MY_VOLUNTEERING),
        Role.COORDINATOR, Set.of(Feature.BROWSE_OPPORTUNITIES, Feature.MANAGE_OPPORTUNITIES,
                                 Feature.RECORD_ATTENDANCE),
        Role.ADMINISTRATOR, Set.of(Feature.BROWSE_OPPORTUNITIES, Feature.MANAGE_COORDINATOR_ACCESS,
                                   Feature.VIEW_PROGRAM_SUMMARIES));

    private final Map<String, User> usersByEmail = new HashMap<>();

    public User addUser(String id, String email, String password, Role role) {
        User user = new User(id, email.toLowerCase(), hash(password), role);
        usersByEmail.put(user.email(), user);
        return user;
    }

    public User login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isEmpty()) {
            throw new ValidationException("Email and password are required.");
        }
        User user = usersByEmail.get(email.toLowerCase());
        if (user == null || !user.passwordHash().equals(hash(password))) {
            throw new ValidationException("Email or password is not correct.");
        }
        return user;
    }

    public boolean canAccess(User user, Feature feature) {
        return ACCESS.get(user.role()).contains(feature);
    }

    public void requireAccess(User user, Feature feature) {
        if (!canAccess(user, feature)) {
            throw new ValidationException("You do not have access to this feature.");
        }
    }

    private static String hash(String password) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
