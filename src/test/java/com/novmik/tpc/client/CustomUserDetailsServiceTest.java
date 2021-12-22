package com.novmik.tpc.client;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  @Mock
  private ClientRepository clientRepository;
  private CustomUserDetailsService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CustomUserDetailsService(clientRepository);
  }

  @Test
  void canLoadUserByUsername() {
    Client client = new Client("test@test.com", "encodedPassword", "T", "E", true, true);
    when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));
    underTest.loadUserByUsername(client.getEmail());
    verify(clientRepository).findByEmail(client.getEmail());
  }

  @Test
  void willThrowWhenNotCanLoadUserByUsername() {
    String email = "test@test.com";
    when(clientRepository.findByEmail(email)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> underTest.loadUserByUsername(email))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage(String.format(
            "Пользователь с электронной почтой: [%s] не найден.", email));
  }
}