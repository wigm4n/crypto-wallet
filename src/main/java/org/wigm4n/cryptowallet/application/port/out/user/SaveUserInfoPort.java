package org.wigm4n.cryptowallet.application.port.out.user;

import org.wigm4n.cryptowallet.domain.UserInfo;

public interface SaveUserInfoPort {

    UserInfo save(UserInfo userInfo);
}
