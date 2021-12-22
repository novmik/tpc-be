package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CheckMatcherPatternSchemeHelperTest {

  @Test
  void canCheckMinMaxDose() {
    String strWithDose = "7,5-15";
    assertThat(CheckMatcherPatternSchemeHelper.checkMinMaxDose(strWithDose)).isTrue();
  }

  @Test
  void canNotCheckMinMaxDoseWhenStrIsNull() {
    assertThat(CheckMatcherPatternSchemeHelper.checkMinMaxDose(null)).isFalse();
  }

  @Test
  void canCheckMinMaxDoseReturnFalse() {
    String strWithDose = "7,515";
    assertThat(CheckMatcherPatternSchemeHelper.checkMinMaxDose(strWithDose)).isFalse();
  }

  @Test
  void canCheckDose() {
    String strWithDose = "7,515";
    assertThat(CheckMatcherPatternSchemeHelper.checkDose(strWithDose)).isTrue();
  }

  @Test
  void canCheckDoseReturnFalse() {
    String strWithDose = "7.515";
    assertThat(CheckMatcherPatternSchemeHelper.checkDose(strWithDose)).isFalse();
  }

  @Test
  void canNotCheckDoseWhenStrIsNull() {
    assertThat(CheckMatcherPatternSchemeHelper.checkDose(null)).isFalse();
  }

  @Test
  void canCheckOnceEveryNxDays() {
    String str = " 1 раз в 15000 дней";
    assertThat(CheckMatcherPatternSchemeHelper.checkOnceEveryNxDays(str)).isTrue();
  }

  @Test
  void canNotCheckOnceEveryNxDaysWhenStrIsNull() {
    assertThat(CheckMatcherPatternSchemeHelper.checkOnceEveryNxDays(null)).isFalse();
  }

  @Test
  void canCheckOnceEveryNxDaysReturnFalseWhenStrWrong() {
    String str = " 1 раз в 1.5 дней";
    assertThat(CheckMatcherPatternSchemeHelper.checkOnceEveryNxDays(str)).isFalse();
  }
}