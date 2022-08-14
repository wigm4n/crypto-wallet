package org.wigm4n.cryptowallet.application.port.out.wallet;

public interface GetWalletTransactionInfoPort {

    Object getTransactionInfo(String walletId, String transactionId);
}
