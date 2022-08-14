package org.wigm4n.cryptowallet.application.port.out.wallet;

import java.util.List;

public interface ListWalletTransactionsPort {

    List<Object> listTransactions(String walletId);
}
