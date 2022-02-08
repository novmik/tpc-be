package com.novmik.tpc.security;

import com.novmik.tpc.client.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Настройка Spring Security.
 */
@Profile("!dev")
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true)
@SuppressWarnings("PMD.LawOfDemeter")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * PUBLIC_URLS.
   */
  protected static final String[] PUBLIC_URLS = {
      "/api/v1/auth/login/**", "/api/v1/auth/refresh"};

  /**
   * {@link CustomUserDetailsService}.
   */
  private final CustomUserDetailsService cudService;
  /**
   * {@link JwtAuthenticationEntryPoint}.
   */
  private final JwtAuthenticationEntryPoint jwtEntryPoint;
  /**
   * {@link JwtAccessDeniedHandler}.
   */
  private final JwtAccessDeniedHandler accessDenied;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(cudService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().accessDeniedHandler(accessDenied)
        .authenticationEntryPoint(jwtEntryPoint).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
//        .and().authorizeRequests().antMatchers("/api/v1/s/**").hasAuthority("ROLE_ADMIN")
        .anyRequest().authenticated().and()
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * Фильтр каждого запроса
   * (для получения токена из заголовка).
   *
   * @return {@link JwtAuthenticationFilter}
   */
  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
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
