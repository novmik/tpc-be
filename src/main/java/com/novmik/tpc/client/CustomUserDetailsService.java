package com.novmik.tpc.client;

import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Загрузка пользовательских данных.
 */
@RequiredArgsConstructor
@Service
@Transactional
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentSize"})
public class CustomUserDetailsService implements UserDetailsService {

  /**
   * {@link ClientRepository}.
   */
  private final ClientRepository clientRepository;

  /**
   * Получить пользователя.
   * Необходим для Spring Security
   *
   * @param email почта клиента
   * @return {@link UserDetails}
   * @throws UsernameNotFoundException если пользователь не найден
   */
  @Override
  public UserDetails loadUserByUsername(final String email) {
    final Optional<Client> dbClient = clientRepository.findByEmail(email);
    return dbClient
        .map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(
            "Пользователь с электронной почтой: [%s] не найден.", email)));
  }
}
