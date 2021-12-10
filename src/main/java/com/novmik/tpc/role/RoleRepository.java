package com.novmik.tpc.role;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Роль data persistence layer operation interface.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  /**
   * Поиск роли по наименованию.
   *
   * @param roleName наименование роли
   * @return роль {@link Role}
   */
  Optional<Role> findRoleByName(String roleName);
}
