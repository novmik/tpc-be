package com.novmik.tpc.medicament;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 * Извлечение дозировки.
 */
@Component
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentSize", "PMD.DataflowAnomalyAnalysis"})
public final class DoseExtractorUtils {

  /**
   * Извлечение строки с дозировкой.
   *
   * @param doseAndDays строка с дозировкой и кол-ом дней
   * @param innDescription МНН описание
   * @param unitOfMeasurement единица измерения
   * @return строку, содержащую дозировку
   */
  public static String getStrDose(
      final String doseAndDays,
      final String innDescription,
      final String unitOfMeasurement) {
    String strWithDose = doseAndDays.substring(0,
        doseAndDays.indexOf(unitOfMeasurement)).trim();
    if (innDescription.contains(" auc ")) {
      strWithDose = doseAndDays.substring(unitOfMeasurement.length() + 1,
          doseAndDays.indexOf(" в") + 1).trim();
    }
    return DescriptionSchemeTransformationUtils.replaceCommasOnPointsInNumbers(strWithDose);
  }

  /**
   * Извлечение дозировки.
   * Проверка содержание строки
   * дозировки ЛП.
   *
   * <p>Pattern: начало строки - число или числа;
   * может быть точка; число или числа;
   * конец строки.
   *
   * <p>Соответствие: 3; 60; 500; 1000; 7.5
   *
   * <p>Несоответствие: 7.5-15; 3.7-4.9; 25-30; 800-1200; 150-180; 5,5
   *
   * @param strWithDose строка с дозировкой
   * @return дозировка
   */
  public static float getDose(final String strWithDose) {
    float dose;
    final Matcher matcher = Pattern.compile("^\\d+\\.?\\d*$")
        .matcher(strWithDose);
    if (matcher.find()) {
      dose = Float.parseFloat(strWithDose);
    } else {
      dose = 0;
    }
    return dose;
  }

  /**
   * Извлечение min и max дозировки.
   * Проверка содержание
   * строки min-max дозировки ЛП.
   *
   * <p>Pattern: начало строки - число или числа;
   * может быть точка; число или числа;
   * обязательно "-"; число или числа;
   * может быть точка; число или числа;
   * конец строки.
   *
   * <p>Соответствие: 7.5-15; 3.7-4.9; 25-30; 800-1200; 150-180
   *
   * <p>Несоответствие: 3; 60; 500; 1000; 7,5; 5.5
   *
   * @param strWithDoses строка с дозировками
   *
   * @return массив с min и max дозировками
   */
  public static float[] getDoseMinMax(final String strWithDoses) {
    float[] doseMinMax = new float[2];
    float doseMin;
    float doseMax;
    final Matcher matcher = Pattern.compile("^(\\d+\\.?\\d*)-(\\d+\\.?\\d*)$")
        .matcher(strWithDoses);
    if (matcher.find()) {
      doseMin = Float.parseFloat(matcher.group(1));
      doseMax = Float.parseFloat(matcher.group(2));
    } else {
      doseMin = 0;
      doseMax = 0;
    }
    doseMinMax[0] = doseMin;
    doseMinMax[1] = doseMax;
    return doseMinMax;
  }

  private DoseExtractorUtils() {
  }
}
