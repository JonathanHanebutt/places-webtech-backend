package com.example.demo.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testGenerateToken() {
        // When
        String token = jwtService.generateToken("testuser");

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT hat 3 Teile
    }

    @Test
    void testExtractUsername() {
        // Given
        String username = "testuser123";
        String token = jwtService.generateToken(username);

        // When
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void testTokenContainsDifferentUsernames() {
        // Given
        String token1 = jwtService.generateToken("user1");
        String token2 = jwtService.generateToken("user2");

        // When
        String username1 = jwtService.extractUsername(token1);
        String username2 = jwtService.extractUsername(token2);

        // Then
        assertEquals("user1", username1);
        assertEquals("user2", username2);
        assertNotEquals(token1, token2);
    }
}

