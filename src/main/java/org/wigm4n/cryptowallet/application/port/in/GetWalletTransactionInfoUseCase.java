package org.wigm4n.cryptowallet.application.port.in;

public interface GetWalletTransactionInfoUseCase {

    Object getInfo(String walletId, String transactionId);
}
