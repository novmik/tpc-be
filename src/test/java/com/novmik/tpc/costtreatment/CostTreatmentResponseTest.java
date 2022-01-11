package com.novmik.tpc.costtreatment;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CostTreatmentResponseTest {

  DiagnosisRelatedGroups drg;
  CostTreatmentResponse underTest;

  @BeforeEach
  void setUp() {
    drg = new DiagnosisRelatedGroups(900L, "numberDrg", "nominationDrg", 10F,
        20F);
    underTest = new CostTreatmentResponse(BigDecimal.valueOf(100_000), drg);
  }

  @Test
  void getCostTreatment() {
    assertThat(underTest.getCostTreatment()).isEqualTo("100000");
  }

  @Test
  void getDrg() {
    assertThat(underTest.getDrg()).isEqualTo(drg);
  }

  @Test
  void setCostTreatment() {
    underTest.setCostTreatment(BigDecimal.ONE);
    assertThat(underTest.getCostTreatment()).isEqualTo("1");
  }

  @Test
  void setDrg() {
    underTest.setDrg(null);
    assertThat(underTest.getDrg()).isEqualTo(null);
  }

  @Test
  void testEquals() {
    CostTreatmentResponse test5 = new CostTreatmentResponse(BigDecimal.valueOf(100_000), drg);
    assertThat(underTest.equals(test5)).isTrue();
  }

  @Test
  void canEqual() {
    CostTreatmentResponse test2 = underTest;
    assertThat(underTest.canEqual(test2)).isTrue();
  }

  @Test
  void testHashCode() {
    CostTreatmentResponse test9 = new CostTreatmentResponse(BigDecimal.valueOf(100_000), drg);
    assertThat(underTest.hashCode()).isEqualTo(test9.hashCode());
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo(
        "CostTreatmentResponse(costTreatment=100000, drg=DiagnosisRelatedGroups(idDrg=900, numberDrg=numberDrg, nominationDrg=nominationDrg, rateIntensity=10.0, wageShare=20.0))");
  }
}