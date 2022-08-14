package org.wigm4n.cryptowallet.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal {
    private String userId;
    private String username;
}
