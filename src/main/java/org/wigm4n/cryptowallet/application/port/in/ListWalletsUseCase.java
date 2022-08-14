package org.wigm4n.cryptowallet.application.port.in;

import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.List;

public interface ListWalletsUseCase {

    List<WalletInfo> listWallets();
}
