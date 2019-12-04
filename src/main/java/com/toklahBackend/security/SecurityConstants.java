package com.toklahBackend.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 28_800_000; // 8 hours 
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/register/**";
    public static final String LOGIN = "/user/login/**";
    public static final String ADMIN_SIGN_UP_URL = "/admin/register/**";
    public static final String ADMIN_LOGIN = "/admin/login/**";
    public static final String ADMIN_forgotPassword = "/admin/forgotpassword/**";
    public static final String FORGOTUSERNAME ="/**";
    public static final String FORGOTPASSWORD ="/api/v1/user/**";
}