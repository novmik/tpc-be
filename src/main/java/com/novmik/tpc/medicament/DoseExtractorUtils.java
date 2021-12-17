package com.novmik.tpc.medicament;

import org.springframework.stereotype.Component;

/**
 * Извлечение дозировки.
 */
@Component
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
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
    return strWithDose;
  }

  /**
   * Извлечение дозировки.
   *
   * @param strWithDose строка с дозировкой
   * @return дозировка
   */
  public static float getDose(final String strWithDose) {
    float dose;
    if (CheckMatcherPatternSchemeHelper.checkDose(strWithDose)) {
      dose = Float.parseFloat(
          DescriptionSchemeTransformationUtils
              .replaceCommasOnPointsInNumbers(strWithDose));
    } else {
      dose = 0;
    }
    return dose;
  }

  /**
   * Извлечение min дозировки.
   *
   * @param strWithDoses строка с дозировками
   * @return min дозировка
   */
  public static float getDoseMin(final String strWithDoses) {
    float doseMin;
    if (CheckMatcherPatternSchemeHelper.checkMinMaxDose(strWithDoses)) {
      final String[] splitMinMax =
          DescriptionSchemeTransformationUtils
              .replaceCommasOnPointsInNumbers(strWithDoses)
              .split("-");
      doseMin = Float.parseFloat(splitMinMax[0].trim());
    } else {
      doseMin = 0;
    }
    return doseMin;
  }

  /**
   * Извлечение max дозировки.
   *
   * @param strWithDoses строка с дозировками
   * @return max дозировка
   */
  public static float getDoseMax(final String strWithDoses) {
    float doseMax;
    if (CheckMatcherPatternSchemeHelper.checkMinMaxDose(strWithDoses)) {
      final String[] splitMinMax =
          DescriptionSchemeTransformationUtils
              .replaceCommasOnPointsInNumbers(strWithDoses)
              .split("-");
      doseMax = Float.parseFloat(splitMinMax[1].trim());
    } else {
      doseMax = 0;
    }
    return doseMax;
  }

  private DoseExtractorUtils() {
  }
}
