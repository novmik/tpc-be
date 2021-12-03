package com.novmik.tpc.medicalinstitution;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.IntegrationTestBase;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MedicalInstitutionRepositoryTest extends IntegrationTestBase {

  @Autowired
  private MedicalInstitutionRepository underTest;
  @Autowired
  private SubjectRepository subjectRepository;
  private Subject subject;

  @BeforeEach
  void setUp() {
    subject = new Subject(
        "Test SubjectName",
        1.00D,
        1.00D);
    subject = subjectRepository.save(subject);
  }

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void canGetAllMedicalInstitutionListBySubjectId() {
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        subject.getNameSubject(),
        "Test MedicalInstitutionName",
        2F,
        "1",
        2F,
        "1",
        2F,
        0);
    medicalInstitution = underTest.save(medicalInstitution);
    List<NameMedicalInstitutionAndId> allMedicalInstitutionListBySubjectId = underTest
        .listIdAndMedicalInstitutionNameBySubjectId(subject.getId());
    assertThat(medicalInstitution.getId()).isNotNull();
    assertThat(allMedicalInstitutionListBySubjectId.size()).isEqualTo(1);
  }

  @Test
  void canFindByNameMedicalInstitution() {
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        subject.getNameSubject(),
        "Test MedicalInstitutionName",
        2F,
        "1",
        2F,
        "1",
        2F,
        0);
    medicalInstitution = underTest.save(medicalInstitution);
    MedicalInstitution actualMedicalInstitution = underTest
        .findByNameMedicalInstitutionAndNameSubject(
            medicalInstitution.getNameMedicalInstitution(), medicalInstitution.getNameSubject())
        .orElse(null);
    assertThat(medicalInstitution).hasNoNullFieldsOrProperties();
    assertThat(medicalInstitution).isEqualTo(actualMedicalInstitution);
  }

  @Test
  void notFindByNameMedicalInstitution() {
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        subject.getNameSubject(),
        "Test MedicalInstitutionName",
        2F,
        "1",
        2F,
        "1",
        2F,
        0);
    underTest.save(medicalInstitution);
    Optional<MedicalInstitution> byNameMedicalInstitution = underTest
        .findByNameMedicalInstitutionAndNameSubject(
        "Test NameMedicalInstitution", "Test NameSubject");
    assertThat(byNameMedicalInstitution.isEmpty()).isTrue();
  }
}