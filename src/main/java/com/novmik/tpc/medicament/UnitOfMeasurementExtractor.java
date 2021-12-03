package com.novmik.tpc.medicament;

import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.AUC;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MG;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MGK_DIVIDE_KG;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MG_DIVIDE_KG;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MG_DIVIDE_M_SQUARED;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MILLION_ME;
import static com.novmik.tpc.medicament.UnitOfMeasurementExtractorConstant.MILLION_ME_DIVIDE_M_SQUARED;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
