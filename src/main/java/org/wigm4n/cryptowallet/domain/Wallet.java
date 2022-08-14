package org.wigm4n.cryptowallet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    public Wallet(String walletId) {
        this.walletId = walletId;
        this.balance = "0";
    }

    private String walletId;
    private String balance;
}
