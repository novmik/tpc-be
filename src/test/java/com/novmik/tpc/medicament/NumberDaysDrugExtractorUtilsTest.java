package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;

class NumberDaysDrugExtractorUtilsTest {

  @Test
  void canGetDaysDrug() {
    String innDescription = "бусерелин 3,75 мг 1 раз в 28 дней";
    String unitOfMeasurement = "мг";
    assertThat(NumberDaysDrugExtractorUtils.getDaysDrug(innDescription, unitOfMeasurement))
        .isEqualTo("1 раз в 28 дней");
    innDescription = "карбоплатин auc 2 в 1-й день";
    unitOfMeasurement = "auc";
    assertThat(NumberDaysDrugExtractorUtils.getDaysDrug(innDescription, unitOfMeasurement))
        .isEqualTo("в 1-й день");
  }

  @Test
  void canGetNumberDaysDrug() {
    String strNumberDaysDrug = "ежедневно";
    int numberDays = 30;
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(30);
    strNumberDaysDrug = "3 раза в неделю";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(30);
    strNumberDaysDrug = "1 раз в 2 недели";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(1);
    strNumberDaysDrug = "1 раз в 3 недели";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(1);
    strNumberDaysDrug = "(ТУР)";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(1);
    strNumberDaysDrug = "в 1-й день каждые 3 недели";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(1);
    strNumberDaysDrug = "в 1-й, 3-й, 5-й дни";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(3);
    strNumberDaysDrug = "46-часовая инфузия в 1-2-й дни";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(2);
    strNumberDaysDrug = "в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(20);
    strNumberDaysDrug = "1 раз в 21 день";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(1);
    strNumberDaysDrug = "2 раза в неделю";
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(10);
    strNumberDaysDrug = "2 раза в неделю";
    numberDays = 21;
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug(strNumberDaysDrug, numberDays))
        .isEqualTo(6);
  }

  @Test
  void whenGetNumberDaysDrugReturn0BecauseStrDontMatch() {
    assertThat(NumberDaysDrugExtractorUtils.getNumberDaysDrug("test", 100))
        .isEqualTo(0);
  }
}