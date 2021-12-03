package com.novmik.tpc.costscheme;

import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MG;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MGK_DIVIDE_KG;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MG_DIVIDE_KG;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MG_DIVIDE_M_SQUARED;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MILLION_ME;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MILLION_ME_DIVIDE_M_SQUARED;

import com.novmik.tpc.medicament.Medicament;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
    Калькулятор Дозировки. Расчёт общей дозировки лекарства в зависимости от единицы измерения
    Получает Лекарственный препарат (c дозировкой), Вес, ППТ(Площадь поверхности тела)
 */

@Slf4j
@Component
public class CalculatorDosage {

  protected static BigDecimal getRequiredDose(final Medicament medicament, final Double weight,
      final Double bsa) {
    String unitOfMeasurement = medicament.getUnitOfMeasurement();
    BigDecimal requiredDose;
    double unitDose = 0D;
    if (MG.equals(unitOfMeasurement) || MILLION_ME.equals(unitOfMeasurement)) {
      unitDose = 1D;
    }
    if (MG_DIVIDE_KG.equals(unitOfMeasurement) || MGK_DIVIDE_KG.equals(unitOfMeasurement)) {
      unitDose = weight;
    }
    if (MG_DIVIDE_M_SQUARED.equals(unitOfMeasurement) || MILLION_ME_DIVIDE_M_SQUARED.equals(
        unitOfMeasurement)) {
      unitDose = bsa;
    }
    if (unitDose == 0) {
      log.error("Не определена единица измерения дозировки: " + unitOfMeasurement);
      requiredDose = BigDecimal.ZERO;
    } else {
      float valueDose =
          medicament.getDose() == 0 ? ((medicament.getDoseMin() + medicament.getDoseMax()) / 2)
              : medicament.getDose();
      requiredDose = BigDecimal.valueOf(valueDose * (medicament.getNumberDaysDrug()) * unitDose)
          .setScale(0, RoundingMode.CEILING);
    }
    return requiredDose;
  }

}
