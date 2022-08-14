package org.wigm4n.cryptowallet.application.port.in;

import org.wigm4n.cryptowallet.domain.WalletInfo;

public interface GetWalletInfoUseCase {

    WalletInfo getWallet(String walletId);
}
