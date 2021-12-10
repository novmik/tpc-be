package com.novmik.tpc.medicalinstitution;

import com.novmik.tpc.subject.Subject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * {@link MedicalInstitution}
 * data persistence layer operation interface.
 */
@Repository
public interface MedicalInstitutionRepository extends JpaRepository<MedicalInstitution, Long> {

  /**
   * Список id и наименование {@link MedicalInstitution}
   * Возвращает проекцию {@link NameMedicalInstitutionAndId}.
   *
   * @param idSubject id {@link Subject}
   * @return список {@link NameMedicalInstitutionAndId}
   */
  @Query(value = """
      select mi.id, mi.name_mi from medical_institution as mi
      where name_subject = (select s.name_subject from subject_of_rf as s where id =:idSubject)
      """, nativeQuery = true)
  List<NameMedicalInstitutionAndId> listIdAndMedicalInstitutionNameBySubjectId(Long idSubject);

  /**
   * Поиск {@link MedicalInstitution} по наименование.
   *
   * @param nameMi      наименование {@link MedicalInstitution}
   * @param nameSubject наименование {@link Subject}
   * @return {@link MedicalInstitution}
   */
  Optional<MedicalInstitution> findByNameMiAndNameSubject(String nameMi, String nameSubject);

  /**
   * Поиск {@link MedicalInstitution}
   * по наименование {@link Subject}.
   *
   * @param nameSubject наименование {@link Subject}
   * @return список {@link MedicalInstitution}
   */
  List<MedicalInstitution> findMedicalInstitutionsByNameSubject(String nameSubject);
}
