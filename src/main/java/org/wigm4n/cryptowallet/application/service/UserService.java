package org.wigm4n.cryptowallet.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.port.in.user.GetUserInfoUseCase;
import org.wigm4n.cryptowallet.application.port.out.auth.GetPrincipalPort;
import org.wigm4n.cryptowallet.application.port.out.user.GetUserInfoPort;
import org.wigm4n.cryptowallet.domain.UserInfo;

@Service
@RequiredArgsConstructor
public class UserService implements GetUserInfoUseCase {

    private final GetPrincipalPort getPrincipalPort;
    private final GetUserInfoPort getUserInfoPort;

    @Override
    public UserInfo getUserInfo() {
        var currentUserId = getPrincipalPort.getPrincipal().getUserId();
        var userInfo = getUserInfoPort.getByUserId(currentUserId);
        if (userInfo.isEmpty()) {
            throw new IllegalArgumentException("Не найден пользователь из токена в таблице UserInfo");
        }
        return userInfo.get();
    }
}
