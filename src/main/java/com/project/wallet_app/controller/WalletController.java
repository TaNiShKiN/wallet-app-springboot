package com.project.wallet_app.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.wallet_app.dto.WalletRequest;
import com.project.wallet_app.model.Wallet;
import com.project.wallet_app.service.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<?> operate(@Valid @RequestBody WalletRequest request) {
        BigDecimal balance = walletService.operate(request);
        return ResponseEntity.ok(Map.of("balance", balance));
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<?> balance(@PathVariable UUID id) {
        return ResponseEntity.ok(
                Map.of("balance", walletService.getBalance(id)));
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet() {
        Wallet wallet = walletService.createWallet();
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }
}
