package com.novmik.tpc.client;

import com.novmik.tpc.auth.RegistrationRequest;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.exception.ResourceAlreadyInUseException;
import com.novmik.tpc.refreshtoken.RefreshTokenService;
import com.novmik.tpc.role.Role;
import com.novmik.tpc.role.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.novmik.tpc.client.ClientConstant.*;

@Slf4j
@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public Optional<Client> registerUser(final RegistrationRequest newRegistrationRequest) {
        String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
        if (emailAlreadyExists(newRegistrationRequestEmail)) {
            log.error(EMAIL_ADDRESS_EXISTS + newRegistrationRequestEmail);
            throw new ResourceAlreadyInUseException("Email", "Address", newRegistrationRequestEmail);
        }
        log.info("Попытка регистрации нового пользователя [" + newRegistrationRequestEmail + "]");
        Client newUser = createUser(newRegistrationRequest);
        Client registeredNewUser = saveClient(newUser);
        return Optional.ofNullable(registeredNewUser);
    }

    public Boolean emailAlreadyExists(final String email) {
        return existsByEmail(email);
    }

    public Client saveClient(final Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClient(final String email) {
        return clientRepository.findByEmail(email);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public boolean existsByEmail(final String email) {
        return clientRepository.existsByEmail(email);
    }

    public boolean existsById(final Long idClient) {
        return clientRepository.existsById(idClient);
    }

    public Client createUser(final RegistrationRequest registrationRequest) {
        return new Client(
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                true,
                true);
    }

    public void deleteClient(final Long idClient) {
        if (idClient == null || idClient < 1) {
            throw new BadRequestException(CLIENT_NOT_CORRECT);
        }
        if (!existsById(idClient)) {
            throw new NotFoundException(CLIENT_NOT_EXISTS + idClient);
        }
        clientRepository.deleteById(idClient);
    }

    public void addRoleToClient(final String email, final String roleName) {
        Optional<Client> clientByEmail = this.getClient(email);
        Optional<Role> roleByName = roleService.getRoleByName(roleName);
        if (clientByEmail.isPresent() && roleByName.isPresent()) {
            clientByEmail.get().getRoles().add(roleByName.get());
        } else {
            throw new NotFoundException(CLIENT_OR_ROLE_NOT_EXISTS);
        }
    }

    public void logoutUser(final CustomUserDetails currentUser, final String refreshToken) {
        log.info("Removing refresh token associated with User [" + currentUser + "]");
        refreshTokenService.deleteByRefreshToken(refreshToken);
    }
}
