package org.wigm4n.cryptowallet.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletInfo, Integer> {

    Optional<WalletInfo> findByWalletId(String walletId);

    List<WalletInfo> findAllByUserId(String userId);
}
