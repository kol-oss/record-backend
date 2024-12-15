package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateUserDTO;
import edu.kpi.backend.dto.CreateUserResponseDTO;
import edu.kpi.backend.dto.JwtRequest;
import edu.kpi.backend.entity.User;
import edu.kpi.backend.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public Optional<String> login(JwtRequest authRequest) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            return Optional.empty();
        }

        UserDetails userDetails = this.userService.loadUserByUsername(authRequest.getUsername());
        return this.jwtTokenUtils.generateToken(userDetails).describeConstable();
    }

    public Optional<CreateUserResponseDTO> register(CreateUserDTO createUserDTO) {
        if (this.userService.getUserByName(createUserDTO.getUsername()).isPresent()) {
            return Optional.empty();
        }

        User registered = this.userService.createUser(createUserDTO);

        return Optional.of(new CreateUserResponseDTO(registered.getId(), registered.getName()));
    }
}
