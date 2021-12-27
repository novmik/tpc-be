package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NumberDaysDrugExtractorMatcherTest {

  @Test
  void canGetMatcherInSomeOneDay() {
    String strNumberDaysDrug = "в 5-й день";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherInSomeOneDay(strNumberDaysDrug).matches()).isTrue();
    strNumberDaysDrug = "в 1-й день каждые 3 недели";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherInSomeOneDay(strNumberDaysDrug).matches()).isTrue();
  }

  @Test
  void canGetMatcherInSomeDaysOneTime() {
    String strNumberDaysDrug = "в 1-й, 3-й, 5-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherInSomeDaysOneTime(strNumberDaysDrug).matches()).isTrue();
  }

  @Test
  void canGetMatcherOneRangeOfDays() {
    String strNumberDaysDrug = "в 1-4-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOneRangeOfDays(strNumberDaysDrug).matches()).isTrue();
    strNumberDaysDrug = "46-часовая инфузия в 1-2-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOneRangeOfDays(strNumberDaysDrug).matches()).isTrue();
    strNumberDaysDrug = "в 1-14-й дни каждые 3 недели";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOneRangeOfDays(strNumberDaysDrug).matches()).isTrue();
  }

  @Test
  void whenGetMatcherOneRangeOfDaysReturnFalseBecauseStrDontMatch() {
    String strNumberDaysDrug = "в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOneRangeOfDays(strNumberDaysDrug).matches()).isFalse();
    strNumberDaysDrug = "1-2-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOneRangeOfDays(strNumberDaysDrug).matches()).isFalse();
    strNumberDaysDrug = "в 1-й день";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOneRangeOfDays(strNumberDaysDrug).matches()).isFalse();
  }

  @Test
  void canGetMatcherManyRangesOfDays() {
    String strNumberDaysDrug = "в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherManyRangesOfDays(strNumberDaysDrug).matches()).isTrue();
  }

  @Test
  void whenGetMatcherManyRangesOfDaysReturnFalseBecauseStrDontMatch() {
    String strNumberDaysDrug = "в 22-26-й дни";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherManyRangesOfDays(strNumberDaysDrug).matches()).isFalse();
  }

  @Test
  void canGetMatcherOnceInPeriodDays() {
    String strNumberDaysDrug = "1 раз в 21 день";
    assertThat(NumberDaysDrugExtractorMatcher
        .getMatcherOnceInPeriodDays(strNumberDaysDrug).matches()).isTrue();
  }
}