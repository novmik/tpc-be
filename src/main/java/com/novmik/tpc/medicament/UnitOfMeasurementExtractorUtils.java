package com.novmik.tpc.medicament;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Извлечение единицы
 * измерения дозировки ЛП.
 */
@Slf4j
@Component
public final class UnitOfMeasurementExtractorUtils {

  /**
   * Получить единицу измерения дозировки ЛП.
   *
   * @param descriptionScheme строка c описанием СЛТ
   *                          (ЛП, дозировка, кол-во дней
   *                          применения)
   * @return единицу измерения дозировки ЛП
   */
  public static String getUnit(final String descriptionScheme) {
    String unit;
    if (descriptionScheme.contains(" мг ")) {
      unit = "мг";
    } else if (descriptionScheme.contains(" мг/м² ")) {
      unit = "мг/м²";
    } else if (descriptionScheme.contains(" мг/кг ")) {
      unit = "мг/кг";
    } else if (descriptionScheme.contains(" auc ")) {
      unit = "auc";
    } else if (descriptionScheme.contains(" млн ме/м² ")) {
      unit = "млн ме/м²";
    } else if (descriptionScheme.contains(" млн ме ")) {
      unit = "млн ме";
    } else if (descriptionScheme.contains(" мкг/кг ")) {
      unit = "мкг/кг";
    } else {
      unit = "";
      log.error("Режим дозирования лекарственного препарата не определён.");
    }
    return unit;
  }

  private UnitOfMeasurementExtractorUtils() {
  }
}
