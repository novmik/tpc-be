package com.novmik.tpc.costtreatment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CoefficientSpecificityUtilsTest {

  @Test
  void coefficientSpecificityIs0WhenTypeMi0() {
    float rateIntensity = 1F;
    int typeMi = 0;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(0F);
  }

  @Test
  void canCalculateCoefficientSpecificityWhenTypeMi1() {
    int typeMi = 1;
    float rateIntensity = 1F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1F);
    rateIntensity = 1.8F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.4F);
  }

  @Test
  void canCalculateCoefficientSpecificityWhenTypeMi2() {
    int typeMi = 2;
    float rateIntensity = 2F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.4F);
    rateIntensity = 1.8F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.2F);
  }

  @Test
  void canCalculateCoefficientSpecificityWhenTypeMi3() {
    int typeMi = 3;
    float rateIntensity = 2F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.4F);
    rateIntensity = 1.8F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.0F);
  }

  @Test
  void canCalculateCoefficientSpecificityWhenTypeMi4() {
    int typeMi = 4;
    float rateIntensity = 2F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.4F);
    rateIntensity = 1.6F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.0F);
  }

  @Test
  void canCalculateCoefficientSpecificityWhenTypeMi5() {
    int typeMi = 5;
    float rateIntensity = 2F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(1.4F);
    rateIntensity = 1.8F;
    assertThat(CoefficientSpecificityUtils.calculate(rateIntensity, typeMi)).isEqualTo(0.8F);
  }
}