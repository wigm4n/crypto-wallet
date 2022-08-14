package org.wigm4n.cryptowallet.application.port.out.wallet;

import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.Optional;

public interface GetWalletInfoPort {

    Optional<WalletInfo> get(String walletId);
}
