package com.novmik.tpc.costscheme;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.medicament.Medicament;
import org.junit.jupiter.api.Test;

class CalculatorDosageUtilsTest {

  @Test
  void canGetRequiredDoseWithMinDose() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        75F,
        0F,
        0F,
        "мг/м²",
        1,
        null);
    double weight = 70D;
    double bsa = 1.81;
    assertThat(CalculatorDosageUtils.getRequiredDose(medicament, weight, bsa)).isEqualTo("136");
  }

  @Test
  void canGetRequiredDoseWithMinMaxDose() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        0F,
        10F,
        20F,
        "мг/м²",
        1,
        null);
    double weight = 70D;
    double bsa = 1.81;
    assertThat(CalculatorDosageUtils.getRequiredDose(medicament, weight, bsa)).isEqualTo("28");
  }

  @Test
  void canGetRequiredDoseWithMinDoseAndMg() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        40F,
        0F,
        0F,
        "мг",
        30,
        null);
    double weight = 70D;
    double bsa = 1.81;
    assertThat(CalculatorDosageUtils.getRequiredDose(medicament, weight, bsa)).isEqualTo("1200");
  }

  @Test
  void canGetRequiredDoseWithMinMaxDoseAndMg() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        0F,
        30F,
        60F,
        "мг",
        30,
        null);
    double weight = 70D;
    double bsa = 1.81;
    assertThat(CalculatorDosageUtils.getRequiredDose(medicament, weight, bsa)).isEqualTo("1350");
  }

  @Test
  void getZeroRequiredDoseWithIncorrectUnitOfMeasurement() {
    Medicament medicament = new Medicament(
        "МНН Тест",
        "sh0000",
        100F,
        0F,
        0F,
        "test",
        30,
        null);
    double weight = 70D;
    double bsa = 1.81;
    assertThat(CalculatorDosageUtils.getRequiredDose(medicament, weight, bsa)).isEqualTo("0");
  }

}