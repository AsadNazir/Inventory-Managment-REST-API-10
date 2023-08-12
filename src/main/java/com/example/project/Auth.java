package com.example.project;

import com.example.project.Domain.User;
import com.example.project.Services.UserService;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Auth {

    public static boolean authorize(List<String> role, String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return false;
        }

        final String encodedUserPassword = authHeader.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
        final String[] credentials = usernameAndPassword.split(":");

        UserService userService = new UserService();
        User user = userService.getUser(credentials[0], credentials[1]);

        if (user == null) return false;

        return role.contains(user.getRole());
    }

}

