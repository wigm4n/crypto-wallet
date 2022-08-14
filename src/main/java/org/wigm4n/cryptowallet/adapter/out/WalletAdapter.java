package org.wigm4n.cryptowallet.adapter.out;

import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.port.out.wallet.*;
import org.wigm4n.cryptowallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

@Service
public class WalletAdapter implements GenerateWalletPort, SendCoinsPort, GetWalletBalancePort,
        ListWalletTransactionsPort, GetWalletTransactionInfoPort {

    @Override
    public Wallet generate() {
        // todo: создавать кошелек из bitcoinj
        return new Wallet(UUID.randomUUID().toString());
    }

    @Override
    public String sendCoins(String walletId) {
        // todo: отправлять коины через bitcoinj
        return "transaction_123";
    }

    @Override
    public String getBalance(String walletId) {
        // todo: получить инфу из bitcoinj
        return "0";
    }

    @Override
    public Object getTransactionInfo(String walletId, String transactionId) {
        // todo: получить инфу из bitcoinj
        return "transaction_123";
    }

    @Override
    public List<Object> listTransactions(String walletId) {
        // todo: получить инфу из bitcoinj
        return List.of("transaction_1", "transaction_2", "transaction_3");
    }
}
