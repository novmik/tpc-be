package com.novmik.tpc.client;

import com.novmik.tpc.auth.RegistrationRequest;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.exception.ResourceAlreadyInUseException;
import com.novmik.tpc.refreshtoken.RefreshTokenService;
import com.novmik.tpc.role.Role;
import com.novmik.tpc.role.RoleRepository;
import com.novmik.tpc.role.RoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.novmik.tpc.client.ClientConstant.*;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_CORRECT;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;

@Slf4j
@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public Optional<Client> registerUser(RegistrationRequest newRegistrationRequest) {
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

    public Boolean emailAlreadyExists(String email) {
        return existsByEmail(email);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClient(String email) {
        return clientRepository.findByEmail(email);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    public boolean existsById(Long idClient) {
        return clientRepository.existsById(idClient);
    }

    public Client createUser(RegistrationRequest registrationRequest) {
        return new Client(
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                true,
                true);
    }

    public void deleteClient(Long idClient) {
        if (idClient == null || idClient < 1) {
            throw new BadRequestException(CLIENT_NOT_CORRECT);
        }
        if (!existsById(idClient)) {
            throw new NotFoundException(CLIENT_NOT_EXISTS + idClient);
        }
        clientRepository.deleteById(idClient);
    }

    public void addRoleToClient(String email, String roleName) {
        Optional<Client> clientByEmail = this.getClient(email);
        Optional<Role> roleByName = roleService.getRoleByName(roleName);
        if (clientByEmail.isPresent() && roleByName.isPresent()) {
            clientByEmail.get().getRoles().add(roleByName.get());
        } else {
            throw new NotFoundException(CLIENT_OR_ROLE_NOT_EXISTS);
        }
    }

    public void logoutUser(CustomUserDetails currentUser, String refreshToken) {
        log.info("Removing refresh token associated with User [" + currentUser + "]");
        refreshTokenService.deleteByRefreshToken(refreshToken);
    }
}
