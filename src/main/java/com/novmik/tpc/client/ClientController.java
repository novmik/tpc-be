package com.novmik.tpc.client;

import com.novmik.tpc.auth.OnUserRegistrationCompleteEvent;
import com.novmik.tpc.auth.RegistrationRequest;
import com.novmik.tpc.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class ClientController {

    private final ClientService clientService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/list")
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerUser(@RequestBody final RegistrationRequest registrationRequest) {

        return clientService.registerUser(registrationRequest)
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    log.info("Зарегистрированный пользователь [API[: " + user);
                    return new ResponseEntity<>(user, OK);
                })
                .orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "В БД отсутствует пользователь"));
    }

    @PostMapping("/addrole")
    public void addRoleToClient(@RequestBody final AddRoleToClientRequest addRoleToClientRequest) {
        clientService.addRoleToClient(addRoleToClientRequest.getEmail(), addRoleToClientRequest.getRoleName());
    }

    @DeleteMapping("/{idClient}")
    public void deleteClient(@PathVariable("idClient") final Long idClient) {
        clientService.deleteClient(idClient);
    }
}
