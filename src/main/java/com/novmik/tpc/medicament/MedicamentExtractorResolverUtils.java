package com.novmik.tpc.medicament;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Извлечение списка {@link Medicament}
 * из СЛТ(схемы лекарственной терапии).
 */
@Slf4j
@Component
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
public final class MedicamentExtractorResolverUtils {

  /**
   * Получение список {@link Medicament}.
   *
   * @param scheme {@link SchemePharmacotherapy}
   * @return список {@link Medicament}
   */
  public static List<Medicament> getMedicamentList(final SchemePharmacotherapy scheme) {
    final List<String> medicaments = SeparationStrOfSchemeUtils.getMedicaments(
        scheme.getInnMedicament().toLowerCase(Locale.ROOT));
    if (log.isInfoEnabled()) {
      log.info("Список лекарств: {}", medicaments);
    }
    final String preparedScheme =
        DescriptionSchemeTransformationUtils.removeExcess(scheme, medicaments);
    final List<Medicament> medicamentList = new ArrayList<>();
    final List<String> fromDescription = SeparationStrOfSchemeUtils
        .getMedicamentAndSpecificationFromPreparedDescriptionScheme(preparedScheme);
    if (log.isInfoEnabled()) {
      log.info("Список Название и описание схемы лечения: {} ", fromDescription);
    }
    for (int i = 0; i < medicaments.size(); i++) {
      final String innMedicament = medicaments.get(i);
      final String innDescription = fromDescription.get(i);
      final String unitOfMeasurement = UnitOfMeasurementExtractorUtils.getUnit(innDescription);
      final String doseAndDays = innDescription.split(innMedicament)[1];
      final String strWithDose =
          DoseExtractorUtils.getStrDose(doseAndDays, innDescription, unitOfMeasurement);
      float dose;
      dose = DoseExtractorUtils.getDose(strWithDose);
      float doseMin;
      float doseMax;
      float[] doseMinMax = DoseExtractorUtils.getDoseMinMax(strWithDose);
      doseMin = doseMinMax[0];
      doseMax = doseMinMax[1];
      if (dose == 0 && (doseMin == 0 || doseMax == 0)) {
        log.error("Объёмы дозирования лекарственного препарата не определены.");
      }
      final String strDaysDrug =
          NumberDaysDrugExtractorUtils.getDaysDrug(innDescription, unitOfMeasurement);
      final int numberDaysDrug = NumberDaysDrugExtractorUtils.getNumberDaysDrug(
          strDaysDrug, scheme.getDaysTreatments());
      final String numberCodeScheme = scheme.getCodeScheme();
      medicamentList.add(new Medicament(innMedicament, numberCodeScheme, dose, doseMin, doseMax,
          unitOfMeasurement, numberDaysDrug, BigDecimal.ZERO));
    }
    return medicamentList;
  }

  private MedicamentExtractorResolverUtils() {
  }
}
