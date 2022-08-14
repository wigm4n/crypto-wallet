package org.wigm4n.cryptowallet.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wigm4n.cryptowallet.domain.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByUsername(String username);
}
