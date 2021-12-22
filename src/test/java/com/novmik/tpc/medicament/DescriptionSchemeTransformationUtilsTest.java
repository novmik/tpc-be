package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import java.util.List;
import org.junit.jupiter.api.Test;

class DescriptionSchemeTransformationUtilsTest {

  @Test
  void removeExcess() {
    SchemePharmacotherapy scheme = new SchemePharmacotherapy(
        "sh0001",
        "Inn1 + Inn2",
        "НУЖНО_УДАЛИТЬ Inn1 25 мг/м² в/в в 1-й, 8-й дни + Inn2 6 мг/кг в 1-й день; цикл 21 день",
        1,
        "st19.000",
        "ds19.000");
    assertThat(DescriptionSchemeTransformationUtils.removeExcess(scheme, List.of("inn1", "inn2")))
        .isEqualTo("inn1 25 мг/м² в 1-й, 8-й дни + inn2 6 мг/кг в 1-й день");
  }

  @Test
  void canReplaceCommasOnPointsInNumbers() {
    String strWithCommas = "42,42";
    assertThat(DescriptionSchemeTransformationUtils
        .replaceCommasOnPointsInNumbers(strWithCommas)).isEqualTo("42.42");
  }

  @Test
  void returnStrWhenNumbersNotHaveCommas() {
    String strWithoutCommas = "4242";
    assertThat(DescriptionSchemeTransformationUtils
        .replaceCommasOnPointsInNumbers(strWithoutCommas)).isEqualTo("4242");
  }
}