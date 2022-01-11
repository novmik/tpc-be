package com.novmik.tpc.diagnosis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NomenclatureMedicalCareTest {

  NomenclatureMedicalCare underTest;

  @BeforeEach
  void setUp() {
    underTest = new NomenclatureMedicalCare(300, "Code Medical Care",
        "Name Medical Care");
  }

  @Test
  void getIdNmc() {
    assertThat(underTest.getIdNmc()).isEqualTo(300);
  }

  @Test
  void getCodeMedicalCara() {
    assertThat(underTest.getCodeMedicalCare()).isEqualTo("Code Medical Care");
  }

  @Test
  void getNameMedicalCare() {
    assertThat(underTest.getNameMedicalCare()).isEqualTo("Name Medical Care");
  }

  @Test
  void setIdNmc() {
    underTest.setIdNmc(3000);
    assertThat(underTest.getIdNmc()).isEqualTo(3000);

  }

  @Test
  void setCodeMedicalCara() {
    underTest.setCodeMedicalCare("Set Test CodeMedicalCare");
    assertThat(underTest.getCodeMedicalCare()).isEqualTo("Set Test CodeMedicalCare");
  }

  @Test
  void setNameMedicalCare() {
    underTest.setNameMedicalCare("Set Test NameMedicalCare");
    assertThat(underTest.getNameMedicalCare()).isEqualTo("Set Test NameMedicalCare");
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo(
        "NomenclatureMedicalCare(idNmc=300, codeMedicalCare=Code Medical Care, nameMedicalCare=Name Medical Care)");
  }

  @Test
  void ctorWithoutId() {
    NomenclatureMedicalCare test2 = new NomenclatureMedicalCare("Code Medical Care",
        "Name Medical Care");
  }
}