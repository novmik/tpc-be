package com.novmik.tpc.medicament;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Извленеие кол-ва дней ЛТ.
 */
@Slf4j
@Component
@SuppressWarnings("PMD.LawOfDemeter")
public final class NumberDaysDrugExtractorUtils {

  /**
   * Извлечение кол-ва дней ЛТ.
   *
   * @return строка с кол-ом дней ЛТ
   */
  public static String getDaysDrug(final String innDescription, final String unitOfMeasurement) {
    String strDaysDrug = innDescription
        .replaceAll("\\(нагрузочная доза \\d.*\\)", "")
        .substring(innDescription.indexOf(unitOfMeasurement) + unitOfMeasurement.length());
    if (innDescription.contains(" auc ")) {
      strDaysDrug = innDescription.substring(innDescription.indexOf(" в"));
    }
    return strDaysDrug.trim();
  }

  /**
   * Получить кол-во дней ЛТ.
   *
   * @param strNumberDaysDrug кол-во дней ЛТ
   *                          медиц. препаратом
   * @param numberDays        кол-во дней СЛТ
   * @return кол-во дней ЛТ
   */
  public static int getNumberDaysDrug(
      final String strNumberDaysDrug,
      final int numberDays) {
    int numberDaysDrug;
    if (strNumberDaysDrug.contains("ежедневно")
        || strNumberDaysDrug.contains("3 раза в неделю")) {
      numberDaysDrug = numberDays;
    } else if (NumberDaysDrugExtractorMatcher.getMatcherInSomeOneDay(strNumberDaysDrug).matches()
        || NumberDaysDrugExtractorMatcher.getMatcherOnceInPeriodDays(strNumberDaysDrug).matches()
        || strNumberDaysDrug.contains("1 раз в 2 недели")
        || strNumberDaysDrug.contains("1 раз в 3 недели")
        || strNumberDaysDrug.contains("(ТУР)")) {
      numberDaysDrug = 1;
    } else if (NumberDaysDrugExtractorMatcher.getMatcherInSomeDaysOneTime(strNumberDaysDrug)
        .matches()) {
      Pattern onTheNthDay = Pattern.compile("\\d+-й,?\\s?");
      numberDaysDrug = (int) onTheNthDay.matcher(strNumberDaysDrug).results().count();
    } else if (NumberDaysDrugExtractorMatcher.getMatcherOneRangeOfDays(strNumberDaysDrug)
        .matches()) {
      numberDaysDrug = NumberDaysDrugExtractorHelper.getDaysFromOneRangeOfDays(strNumberDaysDrug);
    } else if (NumberDaysDrugExtractorMatcher.getMatcherManyRangesOfDays(strNumberDaysDrug)
        .matches()) {
      numberDaysDrug = NumberDaysDrugExtractorHelper.getDaysFromManyRangesOfDays(strNumberDaysDrug);
    } else if (strNumberDaysDrug.contains("2 раза в неделю")) {
      numberDaysDrug = NumberDaysDrugExtractorHelper.getDaysFromTwoInPeriodOneWeek(numberDays);
    } else {
      numberDaysDrug = 0;
      log.error(
          "Количество дней введения лекарственных препаратов, оплачиваемых по КСГ, не определено.");
    }
    return numberDaysDrug;
  }

  private NumberDaysDrugExtractorUtils() {
  }
}
