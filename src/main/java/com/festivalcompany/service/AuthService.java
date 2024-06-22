package com.festivalcompany.service;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final Map<String, String> USERS = new HashMap<>();

    static {
        USERS.put("borisz", "1234");
        USERS.put("trisztanv", "1234");
        USERS.put("szunyi", "1234");
    }

    public static boolean authenticate(String username, String password) {
        return USERS.containsKey(username) && USERS.get(username).equals(password);
    }
}