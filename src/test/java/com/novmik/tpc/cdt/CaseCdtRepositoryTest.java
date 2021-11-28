package com.novmik.tpc.cdt;

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
class CaseCdtRepositoryTest {

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