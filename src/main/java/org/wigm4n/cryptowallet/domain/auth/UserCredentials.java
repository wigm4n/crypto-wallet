package org.wigm4n.cryptowallet.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {
    @NotBlank
    @Size(min = 5, max = 50)
    private String username;
    @NotBlank
    @Size(min = 4)
    private String password;
}
