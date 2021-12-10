package com.novmik.tpc.client;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Client} data persistence layer operation interface.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  /**
   * Поиск клиента по email.
   *
   * @param email почта клиента
   * @return {@link Client}
   */
  Optional<Client> findByEmail(String email);

  /**
   * Наличие {@link Client}.
   *
   * @param email почта клиента
   * @return наличие
   */
  Boolean existsByEmail(String email);
}
