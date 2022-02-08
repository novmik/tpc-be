package com.novmik.tpc.auth;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmik.tpc.client.Client;
import com.novmik.tpc.client.CustomUserDetails;
import com.novmik.tpc.refreshtoken.RefreshToken;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@ActiveProfiles("dev")
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AuthenticationManager authenticationManagerBean;
  @MockBean
  private AuthService authService;

  @Test
  @WithMockUser
  void canAuthenticateUser() throws Exception {
    LoginRequest loginRequest = new LoginRequest();
    RefreshToken refreshToken = new RefreshToken(100L, "refreshToken", new Client(), 0L, Instant.now());
    CustomUserDetails customUserDetails = new CustomUserDetails(new Client());
    Authentication auth = new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(),
        loginRequest.getPassword());
    when(authService.authenticateUser(loginRequest)).thenReturn(Optional.of(auth));
    when(authService.generateToken(customUserDetails)).thenReturn("jwtToken");
    when(authService.createAndPersistRefreshToken(auth)).thenReturn(Optional.of(refreshToken));
    mockMvc.perform(post("/api/v1/auth/login")
            .content(asJsonString(loginRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void canRefreshJwtToken() throws Exception {
    TokenRefreshRequest trRequest = new TokenRefreshRequest();
    when(authService.refreshJwtToken(trRequest)).thenReturn(Optional.of("refreshToken"));
    mockMvc.perform(post("/api/v1/auth/refresh")
            .content(asJsonString(trRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}