package com.novmik.tpc.medicament;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.*;

@Slf4j
@Component
public class UnitOfMeasurementExtractor {

    protected static String getUnit(final String medicamentWithDoseAndNumberDaysDrug) {
        String unit = "";
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MG + " ")) {
            unit = MG;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MG_DIVIDE_M_SQUARED + " ")) {
            unit = MG_DIVIDE_M_SQUARED;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MG_DIVIDE_KG + " ")) {
            unit = MG_DIVIDE_KG;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + AUC + " ")) {
            unit = AUC;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MILLION_ME_DIVIDE_M_SQUARED + " ")) {
            unit = MILLION_ME_DIVIDE_M_SQUARED;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MILLION_ME + " ")) {
            unit = MILLION_ME;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MGK_DIVIDE_KG + " ")) {
            unit = MGK_DIVIDE_KG;
        }
        if (unit.equals("")) {
            log.error("Режим дозирования лекарственного препарата не определён.");
        }
        return unit;
    }
}
