package com.likelion.jwt.domain.auth.controller;

import com.likelion.jwt.domain.auth.dto.request.LoginRequest;
import com.likelion.jwt.domain.auth.dto.request.SignupRequest;
import com.likelion.jwt.domain.auth.dto.response.MemberInfoResponse;
import com.likelion.jwt.domain.auth.dto.response.TokenResponse;
import com.likelion.jwt.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getMyInfo(userDetails.getUsername()));
    }
}
