package ch.jevgenijevic.milan.todo.todo_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints for accessing user information")
public class UserInfoController {

    @GetMapping("/me")
    @Operation(summary = "Get current user info", description = "Returns the username and roles of the logged-in user.")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "username", jwt.getClaimAsString("preferred_username"),
                "roles", jwt.getClaimAsMap("realm_access").get("roles")
        );
    }
}
