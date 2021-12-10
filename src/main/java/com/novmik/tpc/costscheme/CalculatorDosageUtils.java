package com.novmik.tpc.costscheme;

import com.novmik.tpc.bsa.BodySurfaceAreaUtils;
import com.novmik.tpc.medicament.Medicament;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Калькулятор Дозировки.
 * Расчёт общей дозировки
 * ЛП в зависимости от
 * единицы измерения
 */
@Slf4j
@Component
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AvoidLiteralsInIfCondition"})
public final class CalculatorDosageUtils {

  /**
   * Расчет требуемой дозировки ЛП.
   *
   * @param medicament {@link Medicament} c дозировкой
   * @param weight     вес человека, кг
   * @param bsa        ППТ {@link BodySurfaceAreaUtils}
   * @return требуемая дозировка ЛП
   */
  public static BigDecimal getRequiredDose(
      final Medicament medicament, final double weight, final double bsa) {
    final String unitOfMeasurement = medicament.getUnitOfMeasurement();
    BigDecimal requiredDose;
    final double unitDose = getUnitDose(weight, bsa, unitOfMeasurement);
    if (unitDose == 0) {
      if (log.isErrorEnabled()) {
        log.error(
            "Не определена единица измерения дозировки: " + unitOfMeasurement);
      }
      requiredDose = BigDecimal.ZERO;
    } else {
      final float sumMinMax = medicament.getDoseMin() + medicament.getDoseMax();
      final float valueDose = medicament.getDose() == 0 ? sumMinMax / 2 : medicament.getDose();
      requiredDose = BigDecimal
          .valueOf(valueDose * (medicament.getNumberDaysDrug()) * unitDose)
          .setScale(0, RoundingMode.CEILING);
    }
    return requiredDose;
  }

  private static double getUnitDose(
      final double weight,
      final double bsa,
      final String unitOfMeasurement
  ) {
    double unitDose;
    if ("мг".equals(unitOfMeasurement) || "млн ме".equals(unitOfMeasurement)) {
      unitDose = 1D;
    } else if ("мг/кг".equals(unitOfMeasurement) || "мкг/кг".equals(unitOfMeasurement)) {
      unitDose = weight;
    } else if ("мг/м²".equals(unitOfMeasurement) || "млн ме/м²".equals(unitOfMeasurement)) {
      unitDose = bsa;
    } else {
      unitDose = 0;
    }
    return unitDose;
  }

  private CalculatorDosageUtils() {
  }
}
