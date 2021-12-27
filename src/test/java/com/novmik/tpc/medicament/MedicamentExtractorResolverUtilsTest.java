package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import org.junit.jupiter.api.Test;

class MedicamentExtractorResolverUtilsTest {

  @Test
  void canGetMedicamentList() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        "sh0001",
        "Inn1 + Inn2",
        "НУЖНО_УДАЛИТЬ Inn1 25 мг/м² в/в в 1-й, 8-й дни + Inn2 6 мг/кг в 1-й день; цикл 21 день",
        1,
        "st19.000",
        "ds19.000");
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).size()).isEqualTo(2);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(0).getDose()).isEqualTo(25);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(0).getNumberDaysDrug()).isEqualTo(2);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(1).getDose()).isEqualTo(6);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(1).getNumberDaysDrug()).isEqualTo(1);
  }

  @Test
  void canGetMedicamentListWith0Dose() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        "sh0001",
        "Inn1 + Inn2",
        "НУЖНО_УДАЛИТЬ Inn1 25 test в/в в 1-й, 8-й дни + Inn2 6 test в 1-й день; цикл 21 день",
        1,
        "st19.000",
        "ds19.000");
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).size()).isEqualTo(2);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(0).getDose()).isEqualTo(0);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(0).getNumberDaysDrug()).isEqualTo(0);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(1).getDose()).isEqualTo(0);
    assertThat(MedicamentExtractorResolverUtils
        .getMedicamentList(scheme).get(1).getNumberDaysDrug()).isEqualTo(0);
  }
}