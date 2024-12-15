package edu.kpi.backend.controller;

import edu.kpi.backend.dto.CreateUserDTO;
import edu.kpi.backend.dto.CreateUserResponseDTO;
import edu.kpi.backend.dto.JwtRequest;
import edu.kpi.backend.dto.JwtResponse;
import edu.kpi.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest authRequest) {
        Optional<String> token = this.authService.login(authRequest);
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Login credentials are invalid");
        }

        return ResponseEntity.ok(new JwtResponse(token.get()));
    }

    @PostMapping("register")
    public ResponseEntity<CreateUserResponseDTO> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Optional<CreateUserResponseDTO> registered = this.authService.register(createUserDTO);
        if (registered.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with name " + createUserDTO.getUsername() + " already exists");
        }

        return ResponseEntity.ok(registered.get());
    }
}
