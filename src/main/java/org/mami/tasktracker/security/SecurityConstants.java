package org.mami.tasktracker.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/user/**";
    public static final String H2_URL = "h2-console/**";
    public static final String[] STATIC_CONTENT_URLS = new String[]{"/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js"};

    // tokens
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_00; // ms
}
