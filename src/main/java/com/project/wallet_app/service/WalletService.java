package com.project.wallet_app.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.project.wallet_app.dto.WalletRequest;
import com.project.wallet_app.enums.OperationType;
import com.project.wallet_app.exceptions.InsufficientFundsException;
import com.project.wallet_app.exceptions.InvalidOperationException;
import com.project.wallet_app.exceptions.WalletNotFoundException;
import com.project.wallet_app.model.Wallet;
import com.project.wallet_app.repository.WalletRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private static final int MAX_RETRIES = 5;

    @Transactional
    public BigDecimal operate(WalletRequest request) {

        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                Wallet wallet = walletRepository.findById(request.getWalletId())
                        .orElseThrow(() -> new WalletNotFoundException());

                OperationType operationType;
                try {
                    operationType = OperationType.valueOf(
                            request.getOperationType().toUpperCase());
                } catch (IllegalArgumentException | NullPointerException e) {
                    throw new InvalidOperationException(
                            "Invalid operation type. Allowed values: DEPOSIT, WITHDRAWAL");
                }

                if (operationType == OperationType.WITHDRAWAL &&
                        wallet.getBalance().compareTo(request.getAmount()) < 0) {
                    throw new InsufficientFundsException();
                }

                if (operationType == OperationType.DEPOSIT) {
                    wallet.setBalance(wallet.getBalance().add(request.getAmount()));
                } else {
                    wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
                }

                return walletRepository.save(wallet).getBalance();

            } catch (ObjectOptimisticLockingFailureException e) {
                if (i == MAX_RETRIES - 1) {
                    throw e;
                }
            }
        }

        throw new IllegalStateException();
    }

    @Transactional
    public BigDecimal getBalance(UUID id) {
        return walletRepository.findById(id)
                .orElseThrow(WalletNotFoundException::new)
                .getBalance();
    }

    @Transactional
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }
}