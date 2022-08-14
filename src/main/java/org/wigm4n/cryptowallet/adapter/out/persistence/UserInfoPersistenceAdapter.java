package org.wigm4n.cryptowallet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.port.out.user.GetUserInfoPort;
import org.wigm4n.cryptowallet.application.port.out.user.SaveUserInfoPort;
import org.wigm4n.cryptowallet.domain.UserInfo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoPersistenceAdapter implements GetUserInfoPort, SaveUserInfoPort {

    private final UserInfoRepository userInfoRepository;

    @Override
    public Optional<UserInfo> getByUserId(String userId) {
        return userInfoRepository.findById(userId);
    }

    @Override
    public Optional<UserInfo> getByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }
}
