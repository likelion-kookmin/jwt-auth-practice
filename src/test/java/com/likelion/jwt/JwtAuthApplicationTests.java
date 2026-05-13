package com.likelion.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest
@ActiveProfiles("test")
class JwtAuthApplicationTests {

    @DynamicPropertySource
    static void configureJwtSecret(DynamicPropertyRegistry registry) {
        String testSecret = Base64.getEncoder()
                .encodeToString("jwt-auth-practice-test-only-signing-key".getBytes(StandardCharsets.UTF_8));
        registry.add("JWT_TEST_SECRET", () -> testSecret);
    }

    @Test
    void contextLoads() {
    }

}
