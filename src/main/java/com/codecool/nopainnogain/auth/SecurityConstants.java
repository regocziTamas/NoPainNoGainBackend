package com.codecool.nopainnogain.auth;

import java.util.Base64;

public class SecurityConstants {
    public static final String SECRET = Base64.getEncoder().encodeToString(System.getenv("AUTH_SECRET").getBytes());
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";


}
