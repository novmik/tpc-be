package com.novmik.tpc.medicament;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Matcher} строки
 * с количеством дней ЛТ.
 */
@SuppressWarnings("PMD.LawOfDemeter")
public final class NumberDaysDrugExtractorMatcher {

  /**
   * {@link Matcher}.
   *
   * <p>Соответствие: "в 5-й день";
   * "в 1-й день каждые 3 недели"
   */
  public static Matcher getMatcherInSomeOneDay(final String strNumberDaysDrug) {
    return Pattern.compile("^в \\d+-й день.*$").matcher(strNumberDaysDrug);
  }

  /**
   * {@link Matcher}.
   *
   * <p>Соответствие: "в 1-й, 3-й, 5-й дни"
   */
  public static Matcher getMatcherInSomeDaysOneTime(final String strNumberDaysDrug) {
    return Pattern.compile("^во? (\\d+-й,?\\s?)+дни$").matcher(strNumberDaysDrug);
  }

  /**
   * {@link Matcher}.
   *
   * <p>Соответствие: "в 1-4-й дни";
   * "46-часовая инфузия в 1-2-й дни";
   * "в 1-14-й дни каждые 3 недели"
   */
  public static Matcher getMatcherOneRangeOfDays(final String strNumberDaysDrug) {
    return Pattern.compile("^.*в \\d+-\\d+-й дни[^,]*$").matcher(strNumberDaysDrug);
  }

  /**
   * {@link Matcher}.
   *
   * <p>Соответствие: "в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни"
   */
  public static Matcher getMatcherManyRangesOfDays(final String strNumberDaysDrug) {
    return Pattern.compile("^в( \\d+-\\d+-й дни,?){2,}$").matcher(strNumberDaysDrug);
  }

  /**
   * {@link Matcher}.
   *
   * <p>Соответствие: "1 раз в 21 день";
   * "1 раз в 28 дней"
   */
  public static Matcher getMatcherOnceInPeriodDays(final String strNumberDaysDrug) {
    return Pattern.compile("^1 раз в (\\d+) \\D+$").matcher(strNumberDaysDrug);
  }

  private NumberDaysDrugExtractorMatcher() {
  }
}
