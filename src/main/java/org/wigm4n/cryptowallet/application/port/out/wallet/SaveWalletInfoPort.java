package org.wigm4n.cryptowallet.application.port.out.wallet;

import org.wigm4n.cryptowallet.domain.WalletInfo;

public interface SaveWalletInfoPort {

    WalletInfo save(WalletInfo walletInfo);
}
