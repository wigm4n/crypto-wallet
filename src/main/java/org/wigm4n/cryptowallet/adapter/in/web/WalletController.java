package org.wigm4n.cryptowallet.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.wigm4n.cryptowallet.application.port.in.*;
import org.wigm4n.cryptowallet.domain.WalletInfo;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final ListWalletsUseCase listWalletsUseCase;
    private final CreateWalletUseCase createWalletUseCase;
    private final GetWalletInfoUseCase getWalletInfoUseCase;
    private final DeleteWalletUseCase deleteWalletUseCase;
    private final GetWalletBalanceUseCase getWalletBalanceUseCase;
    private final ListWalletTransactionsHistoryUseCase listWalletTransactionsHistoryUseCase;
    private final GetWalletTransactionInfoUseCase getWalletTransactionInfoUseCase;
    private final SendCoinsUseCase sendCoinsUseCase;

    @GetMapping("")
    public ResponseEntity<List<WalletInfo>> listWallets() {
        log.info("Принят запрос по ручке GET /api/wallets");
        var response = listWalletsUseCase.listWallets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createWallet() {
        log.info("Принят запрос по ручке POST /api/wallets");
        var response = createWalletUseCase.createWallet();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletInfo> getWallet(
            @PathVariable @NotBlank String walletId
    ) {
        log.info("Принят запрос по ручке GET /api/wallets/{walletId}");
        var response = getWalletInfoUseCase.getWallet(walletId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(
            @PathVariable @NotBlank String walletId
    ) {
        log.info("Принят запрос по ручке DELETE /api/wallets/{walletId}");
        deleteWalletUseCase.deleteWallet(walletId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<String> getBalance(
            @PathVariable @NotBlank String walletId
    ) {
        log.info("Принят запрос по ручке GET /api/wallets/{walletId}/balance");
        var response = getWalletBalanceUseCase.getBalance(walletId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<List<Object>> listTransactionsHistory(
            @PathVariable @NotBlank String walletId
    ) {
        log.info("Принят запрос по ручке GET /api/wallets/{walletId}/transactions");
        var response = listWalletTransactionsHistoryUseCase.listTransactions(walletId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    public ResponseEntity<Object> getTransactionInfo(
            @PathVariable @NotBlank String walletId,
            @PathVariable @NotBlank String transactionId
    ) {
        log.info("Принят запрос по ручке GET /api/wallets/{walletId}/transactions/{transactionId}");
        var response = getWalletTransactionInfoUseCase.getInfo(walletId, transactionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{walletId}/coins:send")
    public ResponseEntity<String> sendCoins(
            @PathVariable @NotBlank String walletId
    ) {
        log.info("Принят запрос по ручке POST /api/wallets/{walletId}/coins:send");
        var response = sendCoinsUseCase.sendCoins(walletId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
