package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NumberDaysDrugExtractorHelperTest {

  @Test
  void canGetDaysFromOneRangeOfDays() {
    String strNumberDaysDrug = "в 1-4-й дни";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromOneRangeOfDays(strNumberDaysDrug)).isEqualTo(4);
    strNumberDaysDrug = "46-часовая инфузия в 1-2-й дни";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromOneRangeOfDays(strNumberDaysDrug)).isEqualTo(2);
    strNumberDaysDrug = "в 1-99-й дни каждые 3 недели";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromOneRangeOfDays(strNumberDaysDrug)).isEqualTo(99);
  }

  @Test
  void whenGetDaysFromOneRangeOfDaysReturn0BecauseStrDontMatch() {
    String strNumberDaysDrug = "в 1-й дни";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromOneRangeOfDays(strNumberDaysDrug)).isEqualTo(0);
    strNumberDaysDrug = "46-часовая инфузия в 1.5-4-й дни";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromOneRangeOfDays(strNumberDaysDrug)).isEqualTo(0);
  }

  @Test
  void canGetDaysFromManyRangesOfDays() {
    String strNumberDaysDrug = "в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromManyRangesOfDays(strNumberDaysDrug)).isEqualTo(20);
  }

  @Test
  void whenGetDaysFromManyRangesOfDaysReturn0BecauseStrDontMatch() {
    String strNumberDaysDrug = "в 1-5-й, 8-12-й, 15-19-й, 22-26-й";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromManyRangesOfDays(strNumberDaysDrug)).isEqualTo(0);
  }

  @Test
  void canGetDaysFromTwoInPeriodOneWeekReturn10WhenNumberDays30() {
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromTwoInPeriodOneWeek(30)).isEqualTo(10);
  }

  @Test
  void canGetDaysFromTwoInPeriodOneWeek() {
    String strNumberDaysDrug = "2 раза в неделю";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromTwoInPeriodOneWeek(21)).isEqualTo(6);
  }

  @Test
  void whenGetDaysFromTwoInPeriodOneWeekReturn0BecauseStrDontMatch() {
    String strNumberDaysDrug = " раза в неделю";
    assertThat(NumberDaysDrugExtractorHelper
        .getDaysFromManyRangesOfDays(strNumberDaysDrug)).isEqualTo(0);
  }
}