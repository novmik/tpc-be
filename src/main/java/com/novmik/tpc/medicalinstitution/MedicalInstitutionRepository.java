package com.novmik.tpc.medicalinstitution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalInstitutionRepository extends JpaRepository<MedicalInstitution, Long> {

    @Query(value = "select mi.id, mi.name_mi from medical_institution as mi where name_subject = (select s.name_subject from subject_of_rf as s where id =:idSubject)", nativeQuery = true)
    List<NameMedicalInstitutionAndId> listIdAndMedicalInstitutionNameBySubjectId(Long idSubject);

    Optional<MedicalInstitution> findByNameMedicalInstitutionAndNameSubject(String nameMedicalInstitution, String nameSubject);

    List<MedicalInstitution> findMedicalInstitutionsByNameSubject(String nameSubject);
}
