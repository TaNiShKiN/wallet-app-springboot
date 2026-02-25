package com.project.wallet_app.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.project.wallet_app.enums.OperationType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {

    @NotNull
    private UUID walletId;

    @NotNull
    // @Schema(description = "Operation type", allowableValues = {"DEPOSIT", "WITHDRAWAL"})
    private String operationType;

    @NotNull
    @Positive
    private BigDecimal amount;
}