package com.novmik.tpc.schemepharmacotherapy;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.IntegrationTestBase;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SchemePharmacotherapyRepositoryTest extends IntegrationTestBase {

  @Autowired
  private SchemePharmacotherapyRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void canFindByCodeScheme() {
    SchemePharmacotherapy schemePharmacotherapy =
        new SchemePharmacotherapy(
            "sh0001",
            "МНН_для_Теста",
            "Описание СЛТ",
            1,
            "st19.000",
            "ds19.000");
    schemePharmacotherapy = underTest.save(schemePharmacotherapy);
    SchemePharmacotherapy actualSpByCodeScheme = underTest
        .findByCodeScheme(schemePharmacotherapy.getCodeScheme()).orElse(null);
    assertThat(schemePharmacotherapy).hasNoNullFieldsOrProperties();
    assertThat(schemePharmacotherapy).isEqualTo(actualSpByCodeScheme);
  }

  @Test
  void notFindByCodeScheme() {
    SchemePharmacotherapy schemePharmacotherapy =
        new SchemePharmacotherapy(
            "sh0001",
            "МНН_для_Теста",
            "Описание СЛТ",
            1,
            "st19.000",
            "ds19.000");
    underTest.save(schemePharmacotherapy);
    Optional<SchemePharmacotherapy> byCodeScheme = underTest
        .findByCodeScheme("sh0000");
    assertThat(byCodeScheme).isEmpty();
  }
}