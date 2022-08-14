package org.wigm4n.cryptowallet.application.port.in;

public interface GetWalletTransactionInfoUseCase {

    Object getTransactionInfo(String walletId, String transactionId);
}
