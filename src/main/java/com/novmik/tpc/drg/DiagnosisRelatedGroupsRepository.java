package com.novmik.tpc.drg;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link DiagnosisRelatedGroups}
 * data persistence layer operation interface.
 */
@Repository
public interface DiagnosisRelatedGroupsRepository extends
    JpaRepository<DiagnosisRelatedGroups, Long> {

  /**
   * Поиск {@link DiagnosisRelatedGroups}.
   *
   * @param numberDrg номер КСГ
   * @return {@link DiagnosisRelatedGroups}
   */
  Optional<DiagnosisRelatedGroups> findByNumberDrg(String numberDrg);
}
