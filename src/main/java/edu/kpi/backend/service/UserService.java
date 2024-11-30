package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateUserDTO;
import edu.kpi.backend.entity.User;
import edu.kpi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.getAll();
    }

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.getById(id);
    }

    public User createUser(CreateUserDTO createUserDTO) {
        return this.userRepository.create(createUserDTO.getName());
    }

    public Optional<User> deleteUserById(UUID id) {
        return this.userRepository.deleteById(id);
    }
}
