package com.novmik.tpc.cdt;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * КСЛП data persistence layer operation interface.
 */
public interface CoefficientDifficultyTreatingRepository extends
    JpaRepository<CoefficientDifficultyTreating, Long> {

  /**
   * Поиск КСЛП.
   *
   * @param nameSubject  наименование субъекта РФ
   * @param careFacility стационар
   * @return список {@link CoefficientDifficultyTreating}
   */
  List<CoefficientDifficultyTreating> findAllByNameSubjectAndCareFacility(String nameSubject,
      String careFacility);

  /**
   * Наличие КСЛП.
   *
   * @param caseCdtId    id случая с КСЛП
   * @param nameSubject  наименование субъекта РФ
   * @param careFacility стационар
   * @return наличие
   */
  @Query(value = """
      select exists(select * from coefficient_difficulty_treating as cdt
      where nomination_case_cdt_id=:caseCdtId
      and name_subject=:nameSubject
      and care_facility =:careFacility)
      """, nativeQuery = true)
  boolean existByCaseCdtIdAndNameSubjectAndCareFacility(Integer caseCdtId, String nameSubject,
      String careFacility);
}
