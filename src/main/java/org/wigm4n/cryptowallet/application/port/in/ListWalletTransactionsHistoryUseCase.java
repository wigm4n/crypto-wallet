package org.wigm4n.cryptowallet.application.port.in;

import java.util.List;

public interface ListWalletTransactionsHistoryUseCase {

    List<Object> listTransactions(String walletId);
}
