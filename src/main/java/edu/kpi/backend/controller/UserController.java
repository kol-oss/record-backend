package edu.kpi.backend.controller;

import edu.kpi.backend.entity.User;
import edu.kpi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(
                this.userService.getAllUsers()
        );
    }

    @GetMapping("{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") UUID userId) {
        Optional<User> user = this.userService.getUserById(userId);

        return user
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user_id") UUID userId) {
        Optional<User> deleted = this.userService.deleteUserById(userId);

        return deleted
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
