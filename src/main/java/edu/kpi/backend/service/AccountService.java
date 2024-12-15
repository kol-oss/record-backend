package edu.kpi.backend.service;

import edu.kpi.backend.entity.Account;
import edu.kpi.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    public Optional<Account> getAccountById(UUID id) {
        return this.accountRepository.findById(id);
    }
}
