package com.novmik.tpc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Настройка Spring Security.
 */
@Profile("dev")
@Slf4j
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true)
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor"})
public class WebSecurityConfigDev extends WebSecurityConfigurerAdapter {

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    log.info("Loaded inside a dev only");
    http.csrf().disable()
    .authorizeRequests().anyRequest().permitAll();
  }

  /**
   * Кодирование паролей.
   *
   * @return {@link BCryptPasswordEncoder}
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}