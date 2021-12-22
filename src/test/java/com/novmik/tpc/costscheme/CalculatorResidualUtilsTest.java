package com.novmik.tpc.costscheme;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalculatorResidualUtilsTest {

  @Test
  void canGetSortedMapResidualIsNotEmpty() {
    List<Float> dosagesMeds = List.of(440F, 150F);
    BigDecimal bdRequiredDose = BigDecimal.valueOf(400D);
    assertThat(CalculatorResidualUtils.getMapResidual(dosagesMeds, bdRequiredDose)).isNotEmpty();
  }

  @Test
  void canGetSortedMapResidualWhen440And150AndRequiredDoseIs400() {
    List<Float> dosagesMeds = List.of(440F, 150F);
    BigDecimal bdRequiredDose = BigDecimal.valueOf(400D);
    assertThat(CalculatorResidualUtils.getMapResidual(dosagesMeds, bdRequiredDose)
        .firstKey()).isEqualTo(40D);
  }

  @Test
  void canGetSortedMapResidualWhen410And150AndRequiredDoseIs400() {
    List<Float> dosagesMeds = List.of(410F, 150F);
    BigDecimal bdRequiredDose = BigDecimal.valueOf(400D);
    assertThat(CalculatorResidualUtils.getMapResidual(dosagesMeds, bdRequiredDose)
        .firstKey()).isEqualTo(10D);
  }

  @Test
  void canGetSortedMapResidualWhen440And300And150AndRequiredDoseIs1210() {
    List<Float> dosagesMeds = List.of(410F, 300F, 150F);
    BigDecimal bdRequiredDose = BigDecimal.valueOf(1210D);
    assertThat(CalculatorResidualUtils.getMapResidual(dosagesMeds, bdRequiredDose)
        .firstKey()).isEqualTo(20D);
  }
}