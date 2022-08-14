package org.wigm4n.cryptowallet.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.port.in.*;
import org.wigm4n.cryptowallet.application.port.out.auth.GetPrincipalPort;
import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase, DeleteWalletUseCase, GetWalletBalanceUseCase,
        GetWalletInfoUseCase, GetWalletTransactionInfoUseCase, ListWalletsUseCase,
        ListWalletTransactionsHistoryUseCase, SendCoinsUseCase {

    private final GetPrincipalPort getPrincipalPort;

    @Override
    public String getBalance(String walletId) {
        var user = getPrincipalPort.getPrincipal();
        return "0";
    }

    @Override
    public String createWallet() {
        return null;
    }

    @Override
    public void deleteWallet(String walletId) {

    }

    @Override
    public WalletInfo getWallet(String walletId) {
        return null;
    }

    @Override
    public Object getInfo(String walletId, String transactionId) {
        return null;
    }

    @Override
    public List<Object> listTransactions(String walletId) {
        return null;
    }

    @Override
    public List<WalletInfo> listWallets() {
        return null;
    }

    @Override
    public String sendCoins(String walletId) {
        return null;
    }
}
