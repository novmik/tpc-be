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
}
