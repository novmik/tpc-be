package com.novmik.tpc.medicament;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 * Проверка СЛП соответствию шаблону.
 */
@Component
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentSize"})
public final class CheckMatcherPatternSchemeHelper {

  /**
   * Проверка содержание
   * строки min-max дозировки ЛП.
   *
   * <p>Pattern: начало строки - число или числа;
   * может быть запятая; число или числа;
   * обязательно "-"; число или числа;
   * может быть запятая; число или числа;
   * конец строки.
   *
   * <p>Соответствие: 7,5-15; 3,7-4,9; 25-30; 800-1200; 150-180
   *
   * <p>Несоответствие: 3; 60; 500; 1000; 7,5; 5.5
   *
   * @param strWithDose строка
   * @return соответствие
   */
  public static boolean checkMinMaxDose(final String strWithDose) {
    return strWithDose != null && Pattern.compile("^\\d+,?\\d*-\\d+,?\\d*$")
        .matcher(strWithDose).find();
  }

  /**
   * Проверка содержание строки
   * дозировки ЛП.
   *
   * <p>Pattern: начало строки - число или числа;
   * может быть запятая; число или числа;
   * конец строки.
   *
   * <p>Соответствие: 3; 60; 500; 1000; 7,5
   *
   * <p>Несоответствие: 7,5-15; 3,7-4,9; 25-30; 800-1200; 150-180; 5.5
   *
   * @param strWithDose строка
   * @return соответствие
   */
  public static boolean checkDose(final String strWithDose) {
    return strWithDose != null && Pattern.compile("^\\d+,?\\d*$")
        .matcher(strWithDose).find();
  }

  /**
   * Проверка содержание строки
   * "1 раз в КОЛ-ВО_ДНЕЙ дней".
   *
   * @param str строка
   * @return соответствие
   */
  public static boolean checkOnceEveryNxDays(final String str) {
    return str != null && Pattern.compile(" 1 раз в \\d+ дней")
        .matcher(str).find();
  }

  private CheckMatcherPatternSchemeHelper() {
  }
}
