package ch.jevgenijevic.milan.todo.todo_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin-only routes protected by role-based access")
public class AdminController {

    @GetMapping("/secret")
    @Operation(summary = "Access admin secret", description = "Returns a secret message only accessible to users with the admin role.")
    public String adminSecret() {
        return "Only admins can see this!";
    }
}
