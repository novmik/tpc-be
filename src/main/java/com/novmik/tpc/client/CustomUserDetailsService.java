package com.novmik.tpc.client;

import static com.novmik.tpc.client.ClientConstant.CLIENT_NOT_FOUND_BY_EMAIL;

import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  private final ClientRepository clientRepository;

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    Optional<Client> dbClient = clientRepository.findByEmail(email);
    log.info("Найден пользователь: {}", dbClient);
    return dbClient.map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException(CLIENT_NOT_FOUND_BY_EMAIL + email));
  }
}
