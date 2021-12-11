package com.novmik.tpc.client;

import static org.springframework.http.HttpStatus.OK;

import com.novmik.tpc.auth.RegistrationRequest;
import com.novmik.tpc.exception.UserRegistrationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Клиент control layer.
 */
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@SuppressWarnings("PMD.LawOfDemeter")
public class ClientController {

  /**
   * {@link ClientService}.
   */
  private final ClientService clientService;

  /**
   * Список клиентов.
   * Get-запрос "api/v1/client/list"
   *
   * @return список {@link Client}
   */
  @GetMapping("/list")
  public ResponseEntity<List<Client>> getClients() {
    return new ResponseEntity<>(clientService.getClients(), OK);
  }

  /**
   * Регистрация.
   * Post-запрос "api/v1/client/register"
   *
   * @param regRequest {@link RegistrationRequest}
   * @return {@link Client}
   * @throws UserRegistrationException если клиента не найдено
   */
  @PostMapping("/register")
  public ResponseEntity<Client> registerUser(
      @RequestBody final RegistrationRequest regRequest) {
    return clientService.registerUser(regRequest)
        .map(user -> new ResponseEntity<>(user, OK))
        .orElseThrow(() -> new UserRegistrationException(
            regRequest.getEmail(), "Пользователь не зарегистрирован"));
  }

  /**
   * Добавление роли клиенту.
   * Post-запрос "api/v1/client/addrole"
   *
   * @param addRoleRequest {@link AddRoleToClientRequest}
   */
  @PostMapping("/addrole")
  public void addRoleToClient(@RequestBody final AddRoleToClientRequest addRoleRequest) {
    clientService.addRoleToClient(
        addRoleRequest.getEmail(),
        addRoleRequest.getRoleName());
  }

  /**
   * Удаление клиента.
   * Delete-запрос "api/v1/client/{idClient}"
   *
   * @param idClient id {@link Client}
   */
  @DeleteMapping("/{idClient}")
  public void deleteClient(@PathVariable("idClient") final Long idClient) {
    clientService.deleteClient(idClient);
  }
}
