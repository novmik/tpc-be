package com.novmik.tpc.schemepharmacotherapy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * СЛТ data persistence layer operation interface.
 * (Схема лекарственной терапии)
 */
@Repository
public interface SchemePharmacotherapyRepository extends
    JpaRepository<SchemePharmacotherapy, Long> {

  /**
   * Поиск СЛТ по коду схемы.
   * (Схема лекарственной терапии)
   *
   * @param codeScheme Код схемы лекарственной терапии
   * @return СЛТ {@link SchemePharmacotherapy}
   */
  Optional<SchemePharmacotherapy> findByCodeScheme(String codeScheme);
}
