package org.wigm4n.cryptowallet.application.port.out.user;

import org.wigm4n.cryptowallet.domain.UserInfo;

import java.util.Optional;

public interface GetUserInfoPort {

    Optional<UserInfo> getByUserId(String userId);

    Optional<UserInfo> getByUsername(String username);
}
