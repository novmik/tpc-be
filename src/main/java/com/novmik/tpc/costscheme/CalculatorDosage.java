package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.*;

/*
    Калькулятор Дозировки. Расчёт общей дозировки лекарства в зависимости от единицы измерения
    Получает Лекарственный препарат (c дозировкой), Вес, ППТ(Площадь поверхности тела)
 */

@Slf4j
@Component
public class CalculatorDosage {

    BigDecimal getRequiredDose(Medicament medicament, Double weight, Double bsa) {
        double unitDose = 0D;
        if (medicament.getUnitOfMeasurement().equals(MG) || medicament.getUnitOfMeasurement().equals(MILLION_ME)) {
            unitDose = 1D;
        }
        if (medicament.getUnitOfMeasurement().equals(MG_DIVIDE_KG) || medicament.getUnitOfMeasurement().equals(MGK_DIVIDE_KG)) {
            unitDose = weight;
        }
        if (medicament.getUnitOfMeasurement().equals(MG_DIVIDE_M_SQUARED) || medicament.getUnitOfMeasurement().equals(MILLION_ME_DIVIDE_M_SQUARED)) {
            unitDose = bsa;
        }
        if (unitDose == 0) {
            log.error("Не определена единица измерения дозировки: " + medicament.getUnitOfMeasurement());
            return BigDecimal.ZERO;
        }

        float valueDose = medicament.getDose() == 0 ? ((medicament.getDose_min() + medicament.getDose_max()) / 2) : medicament.getDose();
        return BigDecimal.valueOf(valueDose * (medicament.getNumberDaysDrug()) * unitDose)
                .setScale(0, RoundingMode.CEILING);
    }

}
