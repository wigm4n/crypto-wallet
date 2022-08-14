package org.wigm4n.cryptowallet.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.wigm4n.cryptowallet.application.exception.ExceededMaxNumberOfWalletsPerUserException;
import org.wigm4n.cryptowallet.application.exception.WalletNotFoundException;
import org.wigm4n.cryptowallet.application.port.in.*;
import org.wigm4n.cryptowallet.application.port.out.auth.GetPrincipalPort;
import org.wigm4n.cryptowallet.application.port.out.wallet.*;
import org.wigm4n.cryptowallet.domain.WalletInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase, DeleteWalletUseCase, GetWalletBalanceUseCase,
        GetWalletInfoUseCase, GetWalletTransactionInfoUseCase, ListWalletsUseCase,
        ListWalletTransactionsHistoryUseCase, SendCoinsUseCase {

    private final GetPrincipalPort getPrincipalPort;
    private final ListUserWalletsPort listUserWalletsPort;
    private final SaveWalletInfoPort saveWalletInfoPort;
    private final GenerateWalletPort generateWalletPort;
    private final GetWalletInfoPort getWalletInfoPort;
    private final DeleteWalletPort deleteWalletPort;
    private final GetWalletBalancePort getWalletBalancePort;
    private final ListWalletTransactionsPort listWalletTransactionsPort;
    private final GetWalletTransactionInfoPort getWalletTransactionInfoPort;
    private final SendCoinsPort sendCoinsPort;

    private static final int MAX_WALLETS_COUNT_PER_USER = 10;

    @Override
    public List<WalletInfo> listWallets() {
        var user = getPrincipalPort.getPrincipal();
        return listUserWalletsPort.list(user.getUserId());
    }

    @Override
    public String createWallet() {
        var user = getPrincipalPort.getPrincipal();
        if (listUserWalletsPort.list(user.getUserId()).size() >= MAX_WALLETS_COUNT_PER_USER) {
            throw new ExceededMaxNumberOfWalletsPerUserException(
                    "Для пользователя достигнуто максимальное количество кошельков"
            );
        }
        var generatedWallet = generateWalletPort.generate();
        var walletInfo = new WalletInfo(user.getUserId(), generatedWallet.getWalletId());
        return saveWalletInfoPort.save(walletInfo).getWalletId();
    }

    @Override
    public void deleteWallet(String walletId) {
        checkWalletExistsAndUserHasAccessToIt(walletId);
        deleteWalletPort.delete(walletId);
    }

    @Override
    public WalletInfo getWallet(String walletId) {
        checkWalletExistsAndUserHasAccessToIt(walletId);
        return getWalletInfoPort.get(walletId).get();
    }

    @Override
    public String getBalance(String walletId) {
        checkWalletExistsAndUserHasAccessToIt(walletId);
        return getWalletBalancePort.getBalance(walletId);
    }

    @Override
    public List<Object> listTransactions(String walletId) {
        checkWalletExistsAndUserHasAccessToIt(walletId);
        return listWalletTransactionsPort.listTransactions(walletId);
    }

    @Override
    public Object getTransactionInfo(String walletId, String transactionId) {
        checkWalletExistsAndUserHasAccessToIt(walletId);
        return getWalletTransactionInfoPort.getTransactionInfo(walletId, transactionId);
    }

    @Override
    public String sendCoins(String walletId) {
        checkWalletExistsAndUserHasAccessToIt(walletId);
        return sendCoinsPort.sendCoins(walletId);
    }

    private void checkWalletExistsAndUserHasAccessToIt(String walletId) {
        var user = getPrincipalPort.getPrincipal();
        var walletInfo = getWalletInfoPort.get(walletId);
        if (walletInfo.isEmpty()) {
            throw new WalletNotFoundException(
                    String.format("Кошелька %s не существует", walletId)
            );
        }
        if (!walletInfo.get().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException(
                    String.format("Кошелек %s не принадлежит пользователю %s", walletId, user.getUserId())
            );
        }
    }
}
