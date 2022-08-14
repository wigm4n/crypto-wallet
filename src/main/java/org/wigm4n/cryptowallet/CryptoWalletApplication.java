package org.wigm4n.cryptowallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("org.wigm4n.cryptowallet.config")
public class CryptoWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoWalletApplication.class, args);
    }
}
