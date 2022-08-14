package org.wigm4n.cryptowallet.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wigm4n.cryptowallet.application.port.in.auth.LoginUserUseCase;
import org.wigm4n.cryptowallet.application.port.in.auth.RefreshJwtUseCase;
import org.wigm4n.cryptowallet.application.port.in.auth.RegisterUserUseCase;
import org.wigm4n.cryptowallet.domain.auth.Jwt;
import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final RefreshJwtUseCase refreshJwtUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserCredentials userCredentials) {
        log.info("Принят запрос по ручке POST /api/auth/register");
        registerUserUseCase.register(userCredentials);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Jwt> login(@RequestBody @Valid UserCredentials userCredentials) {
        log.info("Принят запрос по ручке POST /api/auth/login");
        var response = loginUserUseCase.login(userCredentials);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/token:refresh")
    public ResponseEntity<Jwt> refreshToken(@RequestBody @Valid RefreshToken refreshToken) {
        log.info("Принят запрос по ручке POST /api/auth/token:refresh");
        var response = refreshJwtUseCase.refreshJwt(refreshToken.getRefreshToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        log.info("Принят запрос по ручке GET /api/auth/ping");
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class RefreshToken {
        @NotBlank
        private String refreshToken;
    }
}
