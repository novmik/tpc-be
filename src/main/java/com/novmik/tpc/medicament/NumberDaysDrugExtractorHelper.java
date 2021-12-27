package com.novmik.tpc.medicament;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Извленеие кол-ва дней ЛТ.
 */
@SuppressWarnings("PMD.LawOfDemeter")
public final class NumberDaysDrugExtractorHelper {

  /**
   * Извлечение кол-ва дней ЛТ
   * из строки вида: "в 1-4-й дни";
   * "46-часовая инфузия в 1-2-й дни";
   * "в 1-14-й дни каждые 3 недели".
   *
   * @param strNumberDaysDrug строка с кол-ом дней ЛТ
   * @return кол-во дней ЛТ МП
   */
  public static int getDaysFromOneRangeOfDays(final String strNumberDaysDrug) {
    int countDays;
    int begin;
    int end;
    Matcher matcher = Pattern.compile("^.*в (\\d+)-(\\d+)-й\\sдни[^,]*$")
        .matcher(strNumberDaysDrug);
    if (matcher.find()) {
      begin = Integer.parseInt(matcher.group(1));
      end = Integer.parseInt(matcher.group(2));
      countDays = end - begin + 1;
    } else {
      countDays = 0;
    }
    return countDays;
  }

  /**
   * Извлечение кол-ва дней ЛТ
   * из строки вида:
   * "в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни".
   *
   * @param strNumberDaysDrug строка с кол-ом дней ЛТ МП
   * @return кол-во дней ЛТ МП
   */
  public static int getDaysFromManyRangesOfDays(final String strNumberDaysDrug) {
    int countDays = 0;
    Matcher matcher = Pattern.compile("^в( \\d+-\\d+-й\\sдни,?){2,}$").matcher(strNumberDaysDrug);
    while (matcher.find()) {
      String oneRange = matcher.group();
      int begin;
      int end;
      Matcher matcherRangeOfDays = Pattern.compile("(\\d+)-(\\d+)-й\\sдни").matcher(oneRange);
      while (matcherRangeOfDays.find()) {
        begin = Integer.parseInt(matcherRangeOfDays.group(1));
        end = Integer.parseInt(matcherRangeOfDays.group(2));
        countDays += end - begin + 1;
      }
    }
    return countDays;
  }

  /**
   * Извлечение кол-ва дней ЛТ
   * из строки вида: "2 раза в неделю".
   *
   * @param numberDays кол-во дней всей ЛТ
   * @return кол-во дней ЛТ МП
   */
  public static int getDaysFromTwoInPeriodOneWeek(final int numberDays) {
    int countDays;
    countDays = numberDays == 30 ? 10 : numberDays / 7 * 2;
    return countDays;
  }

  private NumberDaysDrugExtractorHelper() {
  }
}
