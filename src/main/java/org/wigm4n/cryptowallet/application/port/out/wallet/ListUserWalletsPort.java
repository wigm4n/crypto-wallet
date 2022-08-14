package org.wigm4n.cryptowallet.application.port.out.wallet;

import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.List;

public interface ListUserWalletsPort {

    List<WalletInfo> list(String userId);
}
