package com.voluntier.web;

import com.voluntier.model.Opportunity;
import com.voluntier.model.Registration;
import com.voluntier.model.User;
import com.voluntier.service.AuthService;
import com.voluntier.service.OpportunityService;
import com.voluntier.service.RegistrationService;
import com.voluntier.service.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** REST API for the prototype. Each endpoint notes the requirement it serves. */
@RestController
@RequestMapping("/api")
public class ApiController {
    public record LoginRequest(String email, String password) { }
    public record OpportunityRequest(String title, String description, String category,
                                     LocalDateTime start, String location, int capacity) { }

    private final AuthService auth;
    private final OpportunityService opportunities;
    private final RegistrationService registrations;
    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    public ApiController(AuthService auth, OpportunityService opportunities, RegistrationService registrations) {
        this.auth = auth;
        this.opportunities = opportunities;
        this.registrations = registrations;
    }

    /** FR-001 */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        User user = auth.login(req.email(), req.password());
        String token = UUID.randomUUID().toString();
        sessions.put(token, user);
        return Map.of("token", token, "role", user.role().name(), "email", user.email());
    }

    /** FR-002 */
    @GetMapping("/opportunities")
    public List<Map<String, Object>> browse(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                                            @RequestParam(required = false) String search,
                                            @RequestParam(required = false) String category) {
        user(token);
        return opportunities.browse(search, category).stream().map(this::view).toList();
    }

    /** FR-003 */
    @PostMapping("/opportunities/{id}/register")
    public Registration register(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                                 @PathVariable String id) {
        return registrations.register(user(token), id);
    }

    /** FR-004 */
    @DeleteMapping("/opportunities/{id}/register")
    public void withdraw(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                         @PathVariable String id) {
        registrations.withdraw(user(token), id);
    }

    @GetMapping("/my/registrations")
    public List<Registration> mine(@RequestHeader(value = "X-Auth-Token", required = false) String token) {
        return registrations.upcomingFor(user(token));
    }

    /** FR-006 */
    @PostMapping("/opportunities")
    @ResponseStatus(HttpStatus.CREATED)
    public Opportunity create(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                              @RequestBody OpportunityRequest r) {
        return opportunities.create(user(token), r.title(), r.description(), r.category(),
            r.start(), r.location(), r.capacity());
    }

    /** FR-006 */
    @DeleteMapping("/opportunities/{id}")
    public Opportunity cancel(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                              @PathVariable String id) {
        return opportunities.cancel(user(token), id);
    }

    private Map<String, Object> view(Opportunity o) {
        return Map.of("id", o.id(), "title", o.title(), "description", o.description(),
            "category", o.category(), "start", o.start().toString(), "location", o.location(),
            "capacity", o.capacity(), "remaining", registrations.remainingSpaces(o.id()));
    }

    private User user(String token) {
        User u = token == null ? null : sessions.get(token);
        if (u == null) throw new UnauthorizedException();
        return u;
    }

    static class UnauthorizedException extends RuntimeException { }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Map<String, String>> badRequest(ValidationException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<Map<String, String>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please sign in."));
    }
}
