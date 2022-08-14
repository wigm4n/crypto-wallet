package org.wigm4n.cryptowallet.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wigm4n.cryptowallet.application.port.in.user.GetUserInfoUseCase;
import org.wigm4n.cryptowallet.domain.UserInfo;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final GetUserInfoUseCase getUserInfoUseCase;

    @GetMapping("")
    public ResponseEntity<UserInfo> getUserInfo() {
        log.info("Принят запрос по ручке GET /api/user");
        var response = getUserInfoUseCase.getUserInfo();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<String> updateUserInfo() {
        log.info("Принят запрос по ручке PATCH /api/user");
        return new ResponseEntity<>("NOT IMPLEMENTED YET", HttpStatus.OK);
    }
}
