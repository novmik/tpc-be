package com.novmik.tpc.medicament;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.*;

@Slf4j
@Component
public class UnitOfMeasurementExtractor {

    String getUnit(String medicamentWithDoseAndNumberDaysDrug) {
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MG + " ")) {
            return MG;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MG_DIVIDE_M_SQUARED + " ")) {
            return MG_DIVIDE_M_SQUARED;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MG_DIVIDE_KG + " ")) {
            return MG_DIVIDE_KG;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + AUC + " ")) {
            return AUC;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MILLION_ME_DIVIDE_M_SQUARED + " ")) {
            return MILLION_ME_DIVIDE_M_SQUARED;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MILLION_ME + " ")) {
            return MILLION_ME;
        }
        if (medicamentWithDoseAndNumberDaysDrug.contains(" " + MGK_DIVIDE_KG + " ")) {
            return MGK_DIVIDE_KG;
        }
        log.error("Режим дозирования лекарственного препарата не определён.");
        return "";
    }
}
