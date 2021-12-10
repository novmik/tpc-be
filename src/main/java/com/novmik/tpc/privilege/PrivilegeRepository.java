package com.novmik.tpc.privilege;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Privilege}
 * data persistence layer operation interface.
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  /**
   * Поиск {@link Privilege} по наименованию.
   *
   * @param privilegeName наименование {@link Privilege}
   * @return {@link Privilege}
   */
  Optional<Privilege> findByName(String privilegeName);
}
