package com.novmik.tpc.cdt;

import com.novmik.tpc.IntegrationTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CaseCdtRepositoryTest extends IntegrationTestBase {

    @Autowired
    private CaseCdtRepository underTest;

    @AfterEach
    void tearDown() { underTest.deleteAll(); }

    @Test
    void canFindByCaseCDTP() {
        CaseCdt caseCdt = new CaseCdt(
                "Test NominationCaseCdt"
        );
        caseCdt = underTest.save(caseCdt);
        CaseCdt actualCaseCdt = underTest.findByNominationCaseCdt(caseCdt.getNominationCaseCdt()).orElse(null);
        assertThat(caseCdt).hasNoNullFieldsOrProperties();
        assertThat(caseCdt).isEqualTo(actualCaseCdt);
    }

    @Test
    void notFindByCaseCDTP() {
        CaseCdt caseCdt = new CaseCdt(
                "Test NominationCaseCdt"
        );
        caseCdt = underTest.save(caseCdt);
        Optional<CaseCdt> byNominationCaseCdt = underTest.findByNominationCaseCdt("test");
        assertThat(byNominationCaseCdt).isEmpty();
    }
}