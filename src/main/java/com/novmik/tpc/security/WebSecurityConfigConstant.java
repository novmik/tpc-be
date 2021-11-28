package com.novmik.tpc.security;

public class WebSecurityConfigConstant {
    public static final String FORBIDDEN_MESSAGE = "Вам необходимо войти в систему, чтобы получить доступ к этой странице.";
    public static final String ACCESS_DENIED_MESSAGE = "Вы не имеете доступа к этой странице.";
    public static final String[] PUBLIC_URLS = { "/api/v1/auth/login/**", "/api/v1/auth/refresh" };

}
