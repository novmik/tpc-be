package com.novmik.tpc.cdt;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoefficientDifficultyTreatingRepository extends
    JpaRepository<CoefficientDifficultyTreating, Long> {

  List<CoefficientDifficultyTreating> findAllByNameSubjectAndCareFacility(String nameSubject,
      String careFacility);

  @Query(value = """
      select exists(select * from coefficient_difficulty_treating as cdt
      where nomination_case_cdt_id=:caseCdtId
      and name_subject=:nameSubject
      and care_facility =:careFacility)
      """, nativeQuery = true)
  boolean existByCaseCdtIdAndNameSubjectAndCareFacility(Integer caseCdtId, String nameSubject,
      String careFacility);
}
