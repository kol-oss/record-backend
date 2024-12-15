package edu.kpi.backend.controller;

import edu.kpi.backend.entity.Account;
import edu.kpi.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/accounts/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(
                this.accountService.getAllAccounts()
        );
    }

    @GetMapping("{account_id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("account_id") UUID accountId) {
        Optional<Account> account = this.accountService.getAccountById(accountId);

        return account
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
