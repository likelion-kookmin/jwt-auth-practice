package com.likelion.jwt.domain.auth.service;

import com.likelion.jwt.domain.auth.dto.request.LoginRequest;
import com.likelion.jwt.domain.auth.dto.request.SignupRequest;
import com.likelion.jwt.domain.auth.dto.response.MemberInfoResponse;
import com.likelion.jwt.domain.auth.dto.response.TokenResponse;
import com.likelion.jwt.domain.member.entity.Member;
import com.likelion.jwt.domain.member.repository.MemberRepository;
import com.likelion.jwt.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequest request) {
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(member.getUsername(), member.getRole());
        return new TokenResponse(token);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInfo(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new MemberInfoResponse(member.getUsername(), member.getRole());
    }
}
