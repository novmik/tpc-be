package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DoseExtractorUtilsTest {

  @Test
  void getStrDose() {
  }

  @Test
  void canGetDose() {
    String strWithDose = "3";
    assertThat(DoseExtractorUtils.getDose(strWithDose)).isEqualTo(3);
    strWithDose = "1000";
    assertThat(DoseExtractorUtils.getDose(strWithDose)).isEqualTo(1000);
    strWithDose = "7.5";
    assertThat(DoseExtractorUtils.getDose(strWithDose)).isEqualTo(7.5F);
  }

  @Test
  void notCanGetDoseIsIncorrect() {
    String strWithDose = "7.5-15";
    assertThat(DoseExtractorUtils.getDose(strWithDose)).isEqualTo(0);
    strWithDose = "5,5";
    assertThat(DoseExtractorUtils.getDose(strWithDose)).isEqualTo(0);
  }

  @Test
  void getDoseMinMax() {
    String strWithDoses = "7.5-15";
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses).length).isEqualTo(2);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[0]).isEqualTo(7.5F);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[1]).isEqualTo(15F);
    strWithDoses = "800-1200";
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses).length).isEqualTo(2);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[0]).isEqualTo(800);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[1]).isEqualTo(1200);
  }

  @Test
  void notGetDoseMinMax() {
    String strWithDoses = "3";
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses).length).isEqualTo(2);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[0]).isEqualTo(0);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[1]).isEqualTo(0);
    strWithDoses = "5.5";
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses).length).isEqualTo(2);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[0]).isEqualTo(0);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[1]).isEqualTo(0);
    strWithDoses = "7,5";
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses).length).isEqualTo(2);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[0]).isEqualTo(0);
    assertThat(DoseExtractorUtils.getDoseMinMax(strWithDoses)[1]).isEqualTo(0);
  }
}