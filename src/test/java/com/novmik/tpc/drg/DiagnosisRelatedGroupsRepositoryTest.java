package com.novmik.tpc.drg;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class DiagnosisRelatedGroupsRepositoryTest {

    @Autowired
    private DiagnosisRelatedGroupsRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canFindDrgByDrg() {
        DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
                1L,
                "Test NumberDrg",
                "Test NominationDrg",
                2F,
                3F
        );
        diagnosisRelatedGroups = underTest.save(diagnosisRelatedGroups);
        DiagnosisRelatedGroups actualDiagnosisRelatedGroups = underTest.findByNumberDrg(diagnosisRelatedGroups.getNumberDrg()).orElse(null);
        assertThat(diagnosisRelatedGroups).hasNoNullFieldsOrProperties();
        assertThat(diagnosisRelatedGroups).isEqualTo(actualDiagnosisRelatedGroups);
    }

    @Test
    void notFindDrgByDrg() {
        DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
                1L,
                "Test NumberDrg",
                "Test NominationDrg",
                2F,
                3F
        );
        underTest.save(diagnosisRelatedGroups);
        Optional<DiagnosisRelatedGroups> diagnosisRelatedGroupsRepositoryByDrg = underTest.findByNumberDrg("test");
        assertThat(diagnosisRelatedGroupsRepositoryByDrg).isEmpty();
    }
}