package com.novmik.tpc.medicalinstitution;

import com.novmik.tpc.subject.SubjectOfRF;
import com.novmik.tpc.subject.SubjectOfRFRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class MedicalInstitutionRepositoryTest {

    @Autowired
    private MedicalInstitutionRepository underTest;
    @Autowired
    private SubjectOfRFRepository subjectOfRFRepository;
    private SubjectOfRF subjectOfRF;

    @BeforeEach
    void setUp() {
        subjectOfRF = new SubjectOfRF(
                "Test SubjectName",
                1.00D,
                1.00D);
        subjectOfRF = subjectOfRFRepository.save(subjectOfRF);
    }

    @AfterEach
    void tearDown() { underTest.deleteAll(); }

    @Test
    void canGetAllMedicalInstitutionListBySubjectId() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                subjectOfRF.getNameSubject(),
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0);
        medicalInstitution = underTest.save(medicalInstitution);
        List<NameMedicalInstitutionAndId> allMedicalInstitutionListBySubjectId = underTest.listIdAndMedicalInstitutionNameBySubjectId(subjectOfRF.getId());
        assertThat(medicalInstitution.getId()).isNotNull();
        assertThat(allMedicalInstitutionListBySubjectId.size()).isEqualTo(1);
    }

    @Test
    void canFindByNameMedicalInstitution() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                subjectOfRF.getNameSubject(),
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0);
        medicalInstitution = underTest.save(medicalInstitution);
        MedicalInstitution actualMedicalInstitution = underTest.findByNameMedicalInstitutionAndNameSubject(medicalInstitution.getNameMedicalInstitution(),medicalInstitution.getNameSubject()).orElse(null);
        assertThat(medicalInstitution).hasNoNullFieldsOrProperties();
        assertThat(medicalInstitution).isEqualTo(actualMedicalInstitution);
    }

    @Test
    void notFindByNameMedicalInstitution() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                subjectOfRF.getNameSubject(),
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0);
        underTest.save(medicalInstitution);
        Optional<MedicalInstitution> byNameMedicalInstitution = underTest.findByNameMedicalInstitutionAndNameSubject("Test NameMedicalInstitution", "Test NameSubject" );
        assertThat(byNameMedicalInstitution.isEmpty()).isTrue();
    }
}