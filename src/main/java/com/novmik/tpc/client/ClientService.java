package com.novmik.tpc.client;

import com.novmik.tpc.auth.RegistrationRequest;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.exception.ResourceAlreadyInUseException;
import com.novmik.tpc.refreshtoken.RefreshTokenService;
import com.novmik.tpc.role.Role;
import com.novmik.tpc.role.RoleService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * {@link Client} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class ClientService {

  /**
   * {@link ClientRepository}.
   */
  private final ClientRepository clientRepository;
  /**
   * {@link RoleService}.
   */
  private final RoleService roleService;
  /**
   * {@link RefreshTokenService}.
   */
  private final RefreshTokenService rtService;
  /**
   * {@link PasswordEncoder}.
   */
  private final PasswordEncoder passwordEncoder;

  /**
   * Регистрация клиента.
   *
   * @param registerRequest {@link RegistrationRequest}
   * @return {@link Client}
   * @throws ResourceAlreadyInUseException если email занят
   */
  public Optional<Client> registerClient(final RegistrationRequest registerRequest) {
    final String registerEmail = registerRequest.getEmail();
    if (existsByEmail(registerEmail)) {
      throw new ResourceAlreadyInUseException("Email", "Address", registerEmail);
    }
    final Client newUser = createUser(registerRequest);
    final Client registeredNewClient = saveClient(newUser);
    return Optional.ofNullable(registeredNewClient);
  }

  /**
   * Сохранение клиента.
   *
   * @param client {@link Client}
   * @return {@link Client}
   */
  public Client saveClient(final Client client) {
    return clientRepository.save(client);
  }

  /**
   * Поиск клиента по email.
   *
   * @param email почта клиента
   * @return {@link Client}
   */
  public Optional<Client> getClientByEmail(final String email) {
    return clientRepository.findByEmail(email);
  }

  /**
   * Список {@link Client}.
   *
   * @return список {@link Client}
   */
  public List<Client> getClients() {
    return clientRepository.findAll();
  }

  /**
   * Наличие email.
   *
   * @param email почта клиента
   * @return наличие
   */
  public boolean existsByEmail(final String email) {
    return clientRepository.existsByEmail(email);
  }

  /**
   * Наличие по id.
   *
   * @param idClient id {@link Client}
   * @return наличие
   */
  public boolean existsById(final Long idClient) {
    return clientRepository.existsById(idClient);
  }

  /**
   * Создание клиента,
   * Закодировав пароль.
   *
   * @param registerRequest {@link RegistrationRequest}
   * @return {@link Client}
   */
  public Client createUser(final RegistrationRequest registerRequest) {
    return new Client(
        registerRequest.getEmail(),
        passwordEncoder.encode(registerRequest.getPassword()),
        registerRequest.getFirstName(),
        registerRequest.getLastName(),
        true,
        true);
  }

  /**
   * Удаление клиента.
   *
   * @param idClient id {@link Client}
   * @throws BadRequestException если некорректные данные
   * @throws NotFoundException   если {@link Client} не найден
   */
  public void deleteClientById(final Long idClient) {
    if (idClient == null || idClient < 1) {
      throw new BadRequestException("Некорректные данные о Клиенте.");
    }
    if (!existsById(idClient)) {
      throw new NotFoundException("Клиента с таким id/email не существует: " + idClient);
    }
    clientRepository.deleteById(idClient);
  }

  /**
   * Добавление роли клиенту.
   *
   * @param email    почта {@link Client}
   * @param roleName наименование роли
   * @throws NotFoundException если {@link Client} или
   *                           {@link Role} не найден
   */
  public void addRoleToClient(final String email, final String roleName) {
    final Optional<Client> clientByEmail = getClientByEmail(email);
    final Optional<Role> roleByName = roleService.findRoleByName(roleName);
    if (clientByEmail.isPresent() && roleByName.isPresent()) {
      clientByEmail.get().getRoles().add(roleByName.get());
    } else {
      throw new NotFoundException("Клиента или роли не существует.");
    }
  }

  /**
   * Удаление токена обновления.
   *
   * @param refreshToken токен обновления
   */
  public void logoutUser(final String refreshToken) {
    rtService.deleteByRefreshToken(refreshToken);
  }
}
