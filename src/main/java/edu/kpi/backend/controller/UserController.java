package edu.kpi.backend.controller;

import edu.kpi.backend.entity.User;
import edu.kpi.backend.dto.CreateUserDTO;
import edu.kpi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        if (createUserDTO.getName() == null || createUserDTO.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User name is invalid");
        }

        return ResponseEntity.ok(this.userService.createUser(createUserDTO));
    }

    @DeleteMapping("{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user_id") UUID userId) {
        Optional<User> deleted = this.userService.deleteUserById(userId);

        return deleted
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
