package edu.kpi.backend.service;

import edu.kpi.backend.config.SecurityConfig;
import edu.kpi.backend.dto.CreateUserDTO;
import edu.kpi.backend.entity.Account;
import edu.kpi.backend.entity.User;
import edu.kpi.backend.repository.AccountRepository;
import edu.kpi.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> getUserByName(String name) {
        return this.userRepository.findByName(name);
    }

    public User createUser(CreateUserDTO createUserDTO) {
        Account account = new Account();
        User user = new User(
                createUserDTO.getUsername(),
                this.passwordEncoder.encode(createUserDTO.getPassword()),
                account
        );

        this.accountRepository.save(account);
        this.userRepository.save(user);

        return user;
    }

    public Optional<User> deleteUserById(UUID id) {
        Optional<User> user = this.userRepository.findById(id);

        this.userRepository.deleteById(id);

        return user;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = this.getUserByName(name).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s not found", name)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(SecurityConfig.ROLE))
        );
    }
}
