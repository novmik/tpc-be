package com.novmik.tpc.cdt;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Случай КСЛП data persistence layer operation interface.
 */
@Repository
public interface CaseCdtRepository extends JpaRepository<CaseCdt, Integer> {

  /**
   * Поиск случая КСЛП по наименованию.
   *
   * @param nominationCaseCdt наименование случая КСЛП
   * @return случай КСЛП {@link CaseCdt}
   */
  Optional<CaseCdt> findByNominationCaseCdt(String nominationCaseCdt);
}
