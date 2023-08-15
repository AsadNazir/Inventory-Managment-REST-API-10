package com.example.project;

import com.example.project.Domain.User;
import com.example.project.Services.UserService;
import com.example.project.commons.Auth;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthTest {

    @Test
    void testAuthorizeWithValidUserAndRole() {
        UserService userService = mock(UserService.class);
        when(userService.getUser(any(String.class), any(String.class)))
                .thenReturn(new User(1, "username1", "password1", "admin"));

        String authHeader = "Basic " + Base64.getEncoder().encodeToString("username1:password1".getBytes());
        List<String> roles = Arrays.asList("admin");

        assertTrue(Auth.authorize(roles, authHeader));
    }
    @Test
    void testAuthorizeWithInvalidUser() {
        UserService userService = mock(UserService.class);
        when(userService.getUser("username", "wrongPassword")).thenReturn(null);

        String authHeader = "Basic " + Base64.getEncoder().encodeToString("username:wrongPassword".getBytes());
        List<String> roles = Arrays.asList("admin");

        assertFalse(Auth.authorize(roles, authHeader));
    }

    @Test
    void testAuthorizeWithInvalidRole() {
        UserService userService = mock(UserService.class);
        when(userService.getUser("username", "password")).thenReturn(new User(1, "username", "password", "user"));

        String authHeader = "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes());
        List<String> roles = Arrays.asList("admin");

        assertFalse(Auth.authorize(roles, authHeader));
    }

}
