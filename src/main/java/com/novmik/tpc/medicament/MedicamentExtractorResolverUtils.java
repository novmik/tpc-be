package com.novmik.tpc.medicament;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Извлечение списка {@link Medicament}
 * из СЛТ(схемы лекарственной терапии).
 */
@Slf4j
@Component
public final class MedicamentExtractorResolverUtils {

  /**
   * Получение список {@link Medicament}.
   *
   * @param scheme {@link SchemePharmacotherapy}
   * @return список {@link Medicament}
   */
  public static List<Medicament> getMedicamentList(final SchemePharmacotherapy scheme) {
    final List<Medicament> medicamentList = new ArrayList<>();
    final String numberCodeScheme = scheme.getCodeScheme();
    final List<String> medicaments = separationMedicaments(
        scheme.getInnMedicament().toLowerCase(Locale.ROOT));
    if (log.isInfoEnabled()) {
      log.info("Список лекарств: " + medicaments);
    }
    final String preparedScheme =
        removeCycleInEndStr(
            removeStartUnnecessary(
                removeIntravenouslyAndNbps(
                    scheme.getDescription().toLowerCase(Locale.ROOT)),
                medicaments));
    if (log.isInfoEnabled()) {
      log.info("Название и описание схемы: " + preparedScheme);
    }
    final List<String> fromDescription =
        separationMedicamentFromPreparedNameAndDescriptionScheme(preparedScheme);
    if (log.isInfoEnabled()) {
      log.info("Список Название и описание схемы лечения: "
          + fromDescription);
    }
    for (int i = 0; i < medicaments.size(); i++) {
      final String innMedicament = medicaments.get(i);
      final String innDescription = fromDescription.get(i);
      final String unitOfMeasurement = UnitOfMeasurementExtractorUtils.getUnit(innDescription);
      String strDaysDrug = innDescription.substring(
          innDescription.indexOf(unitOfMeasurement) + unitOfMeasurement.length());
      if (innDescription.contains(" auc ")) {
        strDaysDrug = innDescription.substring(innDescription.indexOf(" в"));
      }
      final String doseAndDays = innDescription.split(innMedicament)[1];
      String strWithDose = doseAndDays.substring(0,
          doseAndDays.indexOf(unitOfMeasurement)).trim();
      if (innDescription.contains(" auc ")) {
        strWithDose = doseAndDays.substring(unitOfMeasurement.length() + 1,
            doseAndDays.indexOf(" в") + 1).trim();
      }
      float dose;
      if (CheckMatcherPatternSchemeHelper.checkDose(strWithDose)) {
        dose = Float.parseFloat(replaceCommasOnPointsInNumbers(strWithDose));
      } else {
        dose = 0;
      }
      float doseMin;
      float doseMax;
      if (CheckMatcherPatternSchemeHelper.checkMinMaxDose(strWithDose)) {
        final String[] splitMinMax = replaceCommasOnPointsInNumbers(strWithDose).split("-");
        doseMin = Float.parseFloat(splitMinMax[0].trim());
        doseMax = Float.parseFloat(splitMinMax[1].trim());
      } else {
        doseMin = 0;
        doseMax = 0;
      }
      if (dose == 0 && (doseMin == 0 || doseMax == 0)) {
        log.error("Объёмы дозирования лекарственного препарата не определены.");
      }
      final Integer numberDaysDrug = NumberDaysDrugExtractorUtils.getNumberDaysDrug(
          strDaysDrug, scheme.getDaysTreatments());
      medicamentList.add(new Medicament(innMedicament, numberCodeScheme, dose, doseMin, doseMax,
          unitOfMeasurement, numberDaysDrug, BigDecimal.ZERO));
    }
    return medicamentList;
  }

  private static List<String> separationMedicaments(final String innMedicaments) {
    List<String> separationMeds;
    if (innMedicaments.contains(" + ")) {
      separationMeds = Arrays.asList(innMedicaments.split("(\\s*\\+\\s*)"));
    } else {
      separationMeds = List.of(innMedicaments);
    }
    return separationMeds;
  }

  private static String removeStartUnnecessary(final String description,
      final List<String> medicamentsList) {
    final String medicament = medicamentsList.get(0);
    final int startIndex = description.indexOf(medicament);
    return startIndex > 0 ? description.substring(
        startIndex) : description;
  }

  private static List<String> separationMedicamentFromPreparedNameAndDescriptionScheme(
      final String descriptionScheme) {
    List<String> medsDescription;
    if (descriptionScheme.contains(" + ")) {
      medsDescription = Arrays.asList(
          descriptionScheme.split("(\\s*\\+\\s*)"));
    } else {
      medsDescription = new ArrayList<>();
      medsDescription.add(descriptionScheme);
    }
    return medsDescription;
  }

  private static String removeCycleInEndStr(final String description) {
    return description.split(";\\s\\D*\\s\\d*\\s\\D*$")[0];
  }

  private static String removeIntravenouslyAndNbps(final String description) {
    return description.replaceAll(" в/в ", " ").replaceAll(" ", " ");
  }

  private static String replaceCommasOnPointsInNumbers(final String strWithCommas) {
    String str;
    if (Pattern.compile("\\d+,\\d+").matcher(strWithCommas).find()) {
      str = strWithCommas.replaceAll(",", ".");
    } else {
      str = strWithCommas;
    }
    return str;
  }

  private MedicamentExtractorResolverUtils() {
  }
}
