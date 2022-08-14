package org.wigm4n.cryptowallet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
public class WalletInfo {

    public WalletInfo(String userId, String walletId) {
        this.userId = userId;
        this.walletId = walletId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String userId;
    private String walletId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
