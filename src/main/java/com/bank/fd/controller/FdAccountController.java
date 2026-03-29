package com.bank.fd.controller;

import com.bank.fd.dto.AccountRequest;
import com.bank.fd.dto.CloseAccountResponse;
import com.bank.fd.entity.FdAccount;
import com.bank.fd.model.AccountStatus;
import com.bank.fd.service.FdAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class FdAccountController {

    private final FdAccountService accountService;

    public FdAccountController(FdAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<FdAccount> openAccount(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.openAccount(request));
    }

    @GetMapping
    public ResponseEntity<List<FdAccount>> getAccounts(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) AccountStatus status) {
        return ResponseEntity.ok(accountService.getAccounts(customerId, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FdAccount> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/{id}/accrue")
    public ResponseEntity<FdAccount> triggerAccrual(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.triggerAccrual(id));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<CloseAccountResponse> closeAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.closeAccount(id));
    }
}
