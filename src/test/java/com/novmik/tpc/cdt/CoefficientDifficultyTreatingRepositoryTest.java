package com.novmik.tpc.cdt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.novmik.tpc.cdt.CdtConstant.ROUND_THE_CLOCK_CARE_FACILITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class CoefficientDifficultyTreatingRepositoryTest {

    @Autowired
    private CoefficientDifficultyTreatingRepository underTest;
    @Autowired CaseCdtRepository caseCdtRepository;

    @AfterEach
    void tearDown() {underTest.deleteAll(); }

    @Test
    void canFindAllByNameSubjectAndCareFacility() {
        CaseCdt caseCdt = new CaseCdt("Test NominationCaseCdt");
        caseCdt = caseCdtRepository.save(caseCdt);
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
                caseCdt,
                "Test NominationCaseCdt",
                1F,
                ROUND_THE_CLOCK_CARE_FACILITY
        );
        cdt = underTest.save(cdt);
        List<CoefficientDifficultyTreating> allByNameSubjectAndCareFacility = underTest.findAllByNameSubjectAndCareFacility(cdt.getNameSubject(), cdt.getCareFacility());
        assertThat(cdt.getId()).isNotNull();
        assertThat(allByNameSubjectAndCareFacility.size()).isEqualTo(1);
    }

    @Test
    void returnTrueWhenExistByCaseCdtIdAndNameSubjectAndCareFacility() {
        CaseCdt caseCdt = new CaseCdt("Test NominationCaseCdt");
        caseCdt = caseCdtRepository.save(caseCdt);
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
                caseCdt,
                "Test NominationCaseCdt",
                1F,
                ROUND_THE_CLOCK_CARE_FACILITY
        );
        cdt = underTest.save(cdt);
        boolean existByCaseCdtIdAndNameSubjectAndCareFacility = underTest.existByCaseCdtIdAndNameSubjectAndCareFacility(caseCdt.getId(), cdt.getNameSubject(), cdt.getCareFacility());
        assertThat(existByCaseCdtIdAndNameSubjectAndCareFacility).isTrue();
    }
    @Test
    void returnFalseWhenNotExistByCaseCdtIdAndNameSubjectAndCareFacility() {
        CaseCdt caseCdt = new CaseCdt("Test NominationCaseCdt");
        caseCdt = caseCdtRepository.save(caseCdt);
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
                caseCdt,
                "Test NominationCaseCdt",
                1F,
                ROUND_THE_CLOCK_CARE_FACILITY
        );
        underTest.save(cdt);
        boolean existByCaseCdtIdAndNameSubjectAndCareFacility = underTest.existByCaseCdtIdAndNameSubjectAndCareFacility(1, "Test", "test");
        assertThat(existByCaseCdtIdAndNameSubjectAndCareFacility).isFalse();
    }
}