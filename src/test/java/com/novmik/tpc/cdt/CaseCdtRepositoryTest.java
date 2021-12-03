package com.novmik.tpc.cdt;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.IntegrationTestBase;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CaseCdtRepositoryTest extends IntegrationTestBase {

  @Autowired
  private CaseCdtRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void canFindByCaseCdt() {
    CaseCdt caseCdt = new CaseCdt(
        "Test NominationCaseCdt"
    );
    caseCdt = underTest.save(caseCdt);
    CaseCdt actualCaseCdt = underTest.findByNominationCaseCdt(caseCdt.getNominationCaseCdt())
        .orElse(null);
    assertThat(caseCdt).hasNoNullFieldsOrProperties();
    assertThat(caseCdt).isEqualTo(actualCaseCdt);
  }

  @Test
  void notFindByCaseCdt() {
    CaseCdt caseCdt = new CaseCdt(
        "Test NominationCaseCdt"
    );
    caseCdt = underTest.save(caseCdt);
    Optional<CaseCdt> byNominationCaseCdt = underTest.findByNominationCaseCdt("test");
    assertThat(byNominationCaseCdt).isEmpty();
  }
}