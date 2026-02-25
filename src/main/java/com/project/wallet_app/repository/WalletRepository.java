package com.project.wallet_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.wallet_app.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet , UUID> {
    
}
