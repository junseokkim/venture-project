package com.venture.networking.config.security.jwt;

public class JwtConstant {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 12;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;

    public static final String PROFILE_ID = "profileId";
}
