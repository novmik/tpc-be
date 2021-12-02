package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.*;

/*
    Калькулятор Дозировки. Расчёт общей дозировки лекарства в зависимости от единицы измерения
    Получает Лекарственный препарат (c дозировкой), Вес, ППТ(Площадь поверхности тела)
 */

@Slf4j
@Component
public class CalculatorDosage {

    protected static BigDecimal getRequiredDose(final Medicament medicament, final Double weight, final Double bsa) {
        String unitOfMeasurement = medicament.getUnitOfMeasurement();
        BigDecimal requiredDose;
        double unitDose = 0D;
        if (unitOfMeasurement.equals(MG) || unitOfMeasurement.equals(MILLION_ME)) {
            unitDose = 1D;
        }
        if (unitOfMeasurement.equals(MG_DIVIDE_KG) || unitOfMeasurement.equals(MGK_DIVIDE_KG)) {
            unitDose = weight;
        }
        if (unitOfMeasurement.equals(MG_DIVIDE_M_SQUARED) || unitOfMeasurement.equals(MILLION_ME_DIVIDE_M_SQUARED)) {
            unitDose = bsa;
        }
        if (unitDose == 0) {
            log.error("Не определена единица измерения дозировки: " + unitOfMeasurement);
            requiredDose = BigDecimal.ZERO;
        } else {
            float valueDose = medicament.getDose() == 0 ? ((medicament.getDose_min() + medicament.getDose_max()) / 2) : medicament.getDose();
            requiredDose = BigDecimal.valueOf(valueDose * (medicament.getNumberDaysDrug()) * unitDose)
                    .setScale(0, RoundingMode.CEILING);
        }
        return requiredDose;
    }

}
