package com.novmik.tpc.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.novmik.tpc.auth.RegistrationRequest;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.refreshtoken.RefreshTokenService;
import com.novmik.tpc.role.Role;
import com.novmik.tpc.role.RoleService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;
  @Mock
  private RoleService roleService;
  @Mock
  private RefreshTokenService rtService;
  @Mock
  private PasswordEncoder passwordEncoder;
  private ClientService underTest;

  @BeforeEach
  void setUp() {
    underTest = new ClientService(clientRepository, roleService, rtService, passwordEncoder);
  }

  @Test
  void canRegisterClient() {
  }

  @Test
  void canSaveClient() {
    Client client = new Client("test@test.com", "password", "T", "E", true, true);
    underTest.saveClient(client);
    verify(clientRepository).save(client);
  }

  @Test
  void canGetClientByEmail() {
    String email = "test@test.com";
    underTest.getClientByEmail(email);
    verify(clientRepository).findByEmail(email);
  }

  @Test
  void getClients() {
    underTest.getClients();
    verify(clientRepository).findAll();
  }

  @Test
  void existsByEmail() {
    String email = "test@test.com";
    underTest.existsByEmail(email);
    verify(clientRepository).existsByEmail(email);
  }

  @Test
  void existsById() {
    long idClient = 100L;
    underTest.existsById(idClient);
    verify(clientRepository).existsById(idClient);
  }

  @Test
  void createUser() {
    RegistrationRequest registerRequest = new RegistrationRequest(
        "test@test.com", "password", "T", "E", null);
    when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
    assertThat(underTest.createUser(registerRequest)).isNotNull();
  }

  @Test
  void canDeleteClientById() {
    long idClient = 100L;
    when(underTest.existsById(idClient)).thenReturn(true);
    underTest.deleteClientById(idClient);
    verify(clientRepository).deleteById(idClient);
  }

  @Test
  void willThrowWhenDeleteClientByIdWhenIdIsNotCorrect() {
    long idClient = 0;
    assertThatThrownBy(() -> underTest.deleteClientById(idClient))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректные данные о Клиенте.");
    verify(clientRepository, never()).deleteById(idClient);

  }

  @Test
  void willThrowWhenDeleteClientByIdWhichNotExists() {
    long idClient = 100L;
    when(underTest.existsById(idClient)).thenReturn(false);
    assertThatThrownBy(() -> underTest.deleteClientById(idClient))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Клиента с таким id/email не существует: " + idClient);
    verify(clientRepository, never()).deleteById(idClient);

  }

  @Test
  void addRoleToClient() {
    Collection<Role> roles = new ArrayList<>();
    Client client = new Client(
        100L,
        "test@test.com",
        "password",
        "T",
        "E",
        null,
        null,
        true,
        true,
        roles);
    Role role = new Role(200L, "ROLE_TEST", null);
    String email = client.getEmail();
    String roleName = "ROLE_ADMIN";
    when(underTest.getClientByEmail(email)).thenReturn(Optional.of(client));
    when(roleService.findRoleByName(roleName)).thenReturn(Optional.of(role));
    underTest.addRoleToClient(email, roleName);
    assertThat(client.getRoles()).contains(role);
  }

  @Test
  void logoutUser() {
    String refreshToken = "refreshToken";
    underTest.logoutUser(refreshToken);
    verify(rtService).deleteByRefreshToken(refreshToken);
  }
}