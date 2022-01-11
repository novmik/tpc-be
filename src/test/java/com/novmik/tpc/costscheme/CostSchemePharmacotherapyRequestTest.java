package com.novmik.tpc.costscheme;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.medicament.Medicament;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CostSchemePharmacotherapyRequestTest {

  List<Medicament> medicamentList;
  CostSchemePharmacotherapyRequest underTest;

  @BeforeEach
  void setUp() {
    medicamentList = List.of(new Medicament(
        "Inn medicament",
        "number Code Scheme",
        300F,
        0F,
        0F,
        "мг/кг",
        30,
        BigDecimal.valueOf(1200)));
    underTest = new CostSchemePharmacotherapyRequest(
        "codeScheme",
        medicamentList,
        1D,
        70D,
        1.81D);
  }

  @Test
  void getCodeScheme() {
    assertThat(underTest.getCodeScheme()).isEqualTo("codeScheme");
  }

  @Test
  void getMedicamentList() {
    assertThat(underTest.getMedicamentList()).isEqualTo(medicamentList);
  }

  @Test
  void getRegionalMarkup() {
    assertThat(underTest.getRegionalMarkup()).isEqualTo(1D);
  }

  @Test
  void getWeight() {
    assertThat(underTest.getWeight()).isEqualTo(70D);
  }

  @Test
  void getBsa() {
    assertThat(underTest.getBsa()).isEqualTo(1.81D);
  }

  @Test
  void setCodeScheme() {
    underTest.setCodeScheme("Set Test codeScheme");
    assertThat(underTest.getCodeScheme()).isEqualTo("Set Test codeScheme");
  }

  @Test
  void setMedicamentList() {
    underTest.setMedicamentList(null);
    assertThat(underTest.getMedicamentList()).isNull();
  }

  @Test
  void setRegionalMarkup() {
    underTest.setRegionalMarkup(2D);
    assertThat(underTest.getRegionalMarkup()).isEqualTo(2D);
  }

  @Test
  void setWeight() {
    underTest.setWeight(100D);
    assertThat(underTest.getWeight()).isEqualTo(100D);
  }

  @Test
  void setBsa() {
    underTest.setBsa(2.0D);
    assertThat(underTest.getBsa()).isEqualTo(2.0D);
  }

  @Test
  void testEquals() {
    CostSchemePharmacotherapyRequest test3 = new CostSchemePharmacotherapyRequest(
        "codeScheme",
        medicamentList,
        1D,
        70D,
        1.81D);
    assertThat(underTest.equals(test3)).isTrue();
  }

  @Test
  void canEqual() {
    CostSchemePharmacotherapyRequest test2 = underTest;
    assertThat(underTest.canEqual(test2)).isTrue();
  }

  @Test
  void testHashCode() {
    CostSchemePharmacotherapyRequest test13 = new CostSchemePharmacotherapyRequest(
        "codeScheme",
        medicamentList,
        1D,
        70D,
        1.81D);
    assertThat(underTest.hashCode()).isEqualTo(test13.hashCode());
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo(
        "CostSchemePharmacotherapyRequest(codeScheme=codeScheme, medicamentList=[Medicament(innMedicament=Inn medicament, numberCodeScheme=number Code Scheme, dose=300.0, doseMin=0.0, doseMax=0.0, unitOfMeasurement=мг/кг, numberDaysDrug=30, requiredDose=1200)], regionalMarkup=1.0, weight=70.0, bsa=1.81)");
  }
}