package com.novmik.tpc.security;

/**
 * Constants для пакета security.
 */
@SuppressWarnings("PMD.LongVariable")
public class SecurityConstants {

  /**
   * FORBIDDEN_MESSAGE.
   */
  public static final String FORBIDDEN_MESSAGE =
      "Вам необходимо войти в систему, чтобы получить доступ к этой странице.";
  /**
   * ACCESS_DENIED_MESSAGE.
   */
  public static final String ACCESS_DENIED_MESSAGE =
      "Вы не имеете доступа к этой странице.";
  /**
   * PUBLIC_URLS.
   */
  public static final String[] PUBLIC_URLS = {
      "/api/v1/auth/login/**", "/api/v1/auth/refresh"};
  /**
   * TOKEN_REQUEST_HEADER_PREFIX.
   */
  public static final String TOKEN_REQUEST_HEADER_PREFIX =
      "Bearer ";
  /**
   * NOVMIK_ADMINISTRATION.
   */
  public static final String NOVMIK_ADMINISTRATION =
      "User Management Portal of Treatment Payment Calculator(TPC) (Novmik.com)";
  /**
   * PERMISSIONS.
   */
  public static final String PERMISSIONS =
      "permissions";
  /**
   * JWT.
   */
  public static final String JWT =
      "JWT";
}