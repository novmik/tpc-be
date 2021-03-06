package com.novmik.tpc;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring Boot application startup class.
 * Main entry point
 */
@SpringBootApplication
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class TreatmentPaymentCalculatorApplication {

  /**
   * Main.

   * @param args Command line arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(TreatmentPaymentCalculatorApplication.class, args);
  }

  /**
   * CORS configuration.

   * @return CorsFilter
   */
  @Bean
  public CorsFilter corsFilter() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(List.of(
        "http://localhost:4200",
        "http://tpc.novmik.com.s3-website.eu-central-1.amazonaws.com",
        "http://tpc.novmik.com"));
    corsConfiguration.setAllowedHeaders(
        Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
            "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
            "Access-Control-Request-Method", "Access-Control-Request-Headers"));
    corsConfiguration.setExposedHeaders(
        Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"));
    corsConfiguration.setAllowedMethods(Arrays.asList(
        "GET", "POST", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource urlBasedCors =
        new UrlBasedCorsConfigurationSource();
    urlBasedCors.registerCorsConfiguration(
        "/**", corsConfiguration);
    return new CorsFilter(urlBasedCors);
  }
}
