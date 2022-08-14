package org.wigm4n.cryptowallet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.port.out.wallet.GetWalletInfoPort;
import org.wigm4n.cryptowallet.application.port.out.wallet.SaveWalletInfoPort;
import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements SaveWalletInfoPort, GetWalletInfoPort {

    private final WalletRepository walletRepository;

    @Override
    public Optional<WalletInfo> get(String walletId) {
        return walletRepository.findByWalletId(walletId);
    }

    @Override
    public WalletInfo save(WalletInfo walletInfo) {
        return walletRepository.save(walletInfo);
    }
}
