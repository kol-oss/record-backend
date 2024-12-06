package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateUserDTO;
import edu.kpi.backend.entity.Account;
import edu.kpi.backend.entity.User;
import edu.kpi.backend.repository.AccountRepository;
import edu.kpi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.findById(id);
    }

    public User createUser(CreateUserDTO createUserDTO) {
        Account account = new Account();
        User user = new User(createUserDTO.getName(), account);

        this.accountRepository.save(account);
        this.userRepository.save(user);

        return user;
    }

    public Optional<User> deleteUserById(UUID id) {
        Optional<User> user = this.userRepository.findById(id);

        this.userRepository.deleteById(id);

        return user;
    }
}
