package org.wigm4n.cryptowallet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_info")
@Data
@NoArgsConstructor
public class UserInfo {

    public UserInfo(String id, String username) {
        this.id = id;
        this.username = username;
    }

    @Id
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    private String name;
    private String surname;
    private String patronymic;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
