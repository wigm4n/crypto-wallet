package org.wigm4n.cryptowallet.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.exception.UserAlreadyExistsException;
import org.wigm4n.cryptowallet.application.exception.UserNotFoundException;
import org.wigm4n.cryptowallet.application.port.in.auth.LoginUserUseCase;
import org.wigm4n.cryptowallet.application.port.in.auth.RefreshJwtUseCase;
import org.wigm4n.cryptowallet.application.port.in.auth.RegisterUserUseCase;
import org.wigm4n.cryptowallet.application.port.out.auth.ExchangeJwtPort;
import org.wigm4n.cryptowallet.application.port.out.auth.RefreshJwtPort;
import org.wigm4n.cryptowallet.application.port.out.auth.RegisterNewUserPort;
import org.wigm4n.cryptowallet.application.port.out.user.GetUserInfoPort;
import org.wigm4n.cryptowallet.application.port.out.user.SaveUserInfoPort;
import org.wigm4n.cryptowallet.application.port.out.wallet.GenerateWalletPort;
import org.wigm4n.cryptowallet.application.port.out.wallet.SaveWalletInfoPort;
import org.wigm4n.cryptowallet.domain.UserInfo;
import org.wigm4n.cryptowallet.domain.WalletInfo;
import org.wigm4n.cryptowallet.domain.auth.Jwt;
import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

@Service
@RequiredArgsConstructor
public class AuthService implements RegisterUserUseCase, LoginUserUseCase, RefreshJwtUseCase {

    private final ExchangeJwtPort exchangeJwtPort;
    private final RegisterNewUserPort registerNewUserPort;
    private final RefreshJwtPort refreshJwtPort;
    private final SaveUserInfoPort saveUserInfoPort;
    private final GetUserInfoPort getUserInfoPort;
    private final GenerateWalletPort generateWalletPort;
    private final SaveWalletInfoPort saveWalletInfoPort;

    @Override
    public void register(UserCredentials userCredentials) {
        if (getUserInfoPort.getByUsername(userCredentials.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(
                    String.format("Пользователь с логином %s уже зарегистрирован", userCredentials.getUsername())
            );
        }
        var userId = registerUserAndSave(userCredentials);
        generateWalletAndSave(userId);
    }

    @Override
    public Jwt login(UserCredentials userCredentials) {
        if (getUserInfoPort.getByUsername(userCredentials.getUsername()).isEmpty()) {
            throw new UserNotFoundException(
                    String.format("Пользователь с логином %s еще не зарегистрирован", userCredentials.getUsername())
            );
        }
        return exchangeJwtPort.exchangeJwt(userCredentials);
    }

    @Override
    public Jwt refreshJwt(String refreshToken) {
        return refreshJwtPort.refreshJwt(refreshToken);
    }

    private String registerUserAndSave(UserCredentials userCredentials) {
        var generatedUserId = registerNewUserPort.registerUser(userCredentials);
        var userInfo = new UserInfo(generatedUserId, userCredentials.getUsername());
        return saveUserInfoPort.save(userInfo).getId();
    }

    private void generateWalletAndSave(String userId) {
        var generatedWallet = generateWalletPort.generate();
        var walletInfo = new WalletInfo(userId, generatedWallet.getWalletId());
        saveWalletInfoPort.save(walletInfo);
    }
}
