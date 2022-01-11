package com.novmik.tpc.diagnosis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagnosisPriceTest {

  DiagnosisPrice underTest;
  NomenclatureMedicalCare nomenclatureMc;

  @BeforeEach
  void setUp() {
    nomenclatureMc = new NomenclatureMedicalCare(300, "Code Medical Care",
        "Name Medical Care");
    underTest = new DiagnosisPrice(100L, "Test Name Subject", nomenclatureMc,
        10_000D);
  }

  @Test
  void getIdDp() {
    assertThat(underTest.getIdDp()).isEqualTo(100L);
  }

  @Test
  void getNameSubject() {
    assertThat(underTest.getNameSubject()).isEqualTo("Test Name Subject");
  }

  @Test
  void getNomenclatureMc() {
    assertThat(underTest.getNomenclatureMc()).isEqualTo(nomenclatureMc);
  }

  @Test
  void getPriceMedicalCare() {
    assertThat(underTest.getPriceMedicalCare()).isEqualTo(10_000D);
  }

  @Test
  void setIdDp() {
    underTest.setIdDp(500L);
    assertThat(underTest.getIdDp()).isEqualTo(500L);
  }

  @Test
  void setNameSubject() {
    underTest.setNameSubject("Test Set Name Subject");
    assertThat(underTest.getNameSubject()).isEqualTo("Test Set Name Subject");
  }

  @Test
  void setNomenclatureMc() {
    NomenclatureMedicalCare setNomenclatureMc = new NomenclatureMedicalCare(700,
        "T",
        "E");
    underTest.setNomenclatureMc(setNomenclatureMc);
    assertThat(underTest.getNomenclatureMc()).isEqualTo(setNomenclatureMc);
  }

  @Test
  void setPriceMedicalCare() {
    underTest.setPriceMedicalCare(20_000D);
    assertThat(underTest.getPriceMedicalCare()).isEqualTo(20_000D);
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo(
        "DiagnosisPrice(idDp=100, nameSubject=Test Name Subject, nomenclatureMc=NomenclatureMedicalCare(idNmc=300, codeMedicalCare=Code Medical Care, nameMedicalCare=Name Medical Care), priceMedicalCare=10000.0)");
  }

  @Test
  void ctorWithoutId() {
    DiagnosisPrice test2 = new DiagnosisPrice("Test Name Subject", nomenclatureMc,
        10_000D);
    assertThat(test2.getIdDp()).isNull();
  }
}