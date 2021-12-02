package com.novmik.tpc.medicament;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
@Component
public class MedicamentExtractorResolver {

    static List<Medicament> getMedicamentList(final SchemePharmacotherapy schemePharmacotherapy) {
        List<Medicament> medicamentList = new ArrayList<>();
        String numberCodeScheme = schemePharmacotherapy.getCodeScheme();
        List<String> separatedMedicament = separationMedicaments(schemePharmacotherapy.getInnMedicament().toLowerCase());
        log.info("Список лекарств: " + separatedMedicament);
        String preparedNameAndDescriptionScheme = removeCycleInEndStr(removeStartUnnecessary(removeIntravenouslyAndNBSP(schemePharmacotherapy.getNameAndDescriptionScheme().toLowerCase()), separatedMedicament));
        log.info("Название и описание схемы: " + preparedNameAndDescriptionScheme);
        List<String> separatedMedicamentFromNameAndDescriptionScheme = separationMedicamentFromPreparedNameAndDescriptionScheme(preparedNameAndDescriptionScheme);
        log.info("Список Название и описание схемы лечения: " + separatedMedicamentFromNameAndDescriptionScheme);
        for (int i = 0; i < separatedMedicament.size(); i++) {
            String innMedicament = separatedMedicament.get(i);
            String innDescription = separatedMedicamentFromNameAndDescriptionScheme.get(i);
            float dose = 0;
            float dose_min = 0;
            float dose_max = 0;
            String unitOfMeasurement = UnitOfMeasurementExtractor.getUnit(innDescription);
            String numberDaysDrugTreatments = innDescription.substring(innDescription.indexOf(unitOfMeasurement) + unitOfMeasurement.length());
            if (innDescription.contains(" auc ")) {
                numberDaysDrugTreatments = innDescription.substring(innDescription.indexOf(" в"));
            }
            Integer numberDaysDrug = NumberDaysDrugExtractor.getNumberDaysDrug(numberDaysDrugTreatments, schemePharmacotherapy.getNumberDaysDrugTreatments());
            String doseAndNumberDaysDrug = innDescription.split(innMedicament)[1];
            String strWithDose = doseAndNumberDaysDrug.substring(0, doseAndNumberDaysDrug.indexOf(unitOfMeasurement)).trim();
            if (innDescription.contains(" auc ")) {
                strWithDose = doseAndNumberDaysDrug.substring((unitOfMeasurement.length() + 1), (doseAndNumberDaysDrug.indexOf(" в") + 1)).trim();
            }
            if (CheckMatcherPatternScheme.checkDose(strWithDose)) {
                dose = Float.parseFloat(replaceCommasOnPointsInNumbers(strWithDose));
            }
            if (CheckMatcherPatternScheme.checkMinMaxDose(strWithDose)) {
                String[] splitStrMinMaxDose = replaceCommasOnPointsInNumbers(strWithDose).split("-");
                dose_min = Float.parseFloat(splitStrMinMaxDose[0].trim());
                dose_max = Float.parseFloat(splitStrMinMaxDose[1].trim());
            }
            if (dose == 0 && (dose_min == 0 || dose_max == 0)) {
                log.error("Объёмы дозирования лекарственного препарата не определены.");
            }
            medicamentList.add(new Medicament(innMedicament, numberCodeScheme, dose, dose_min, dose_max, unitOfMeasurement, numberDaysDrug, BigDecimal.ZERO));
        }
        return medicamentList;
    }

    private static List<String> separationMedicaments(final String innMedicaments) {
        if (innMedicaments.contains(" + ")) {
            return Arrays.asList(innMedicaments.split("(\\s*\\+\\s*)"));
        }
        return List.of(innMedicaments);
    }

    private static String removeStartUnnecessary(final String nameAndDescriptionScheme, final List<String> medicamentsList) {
        String medicalPreparation = medicamentsList.get(0);
        int startIndexOfMedicalPreparation = nameAndDescriptionScheme.indexOf(medicalPreparation);
        return startIndexOfMedicalPreparation > 0 ? nameAndDescriptionScheme.substring(startIndexOfMedicalPreparation) : nameAndDescriptionScheme;
    }

    private static List<String> separationMedicamentFromPreparedNameAndDescriptionScheme(final String preparedNameAndDescriptionScheme) {
        List<String> medicamentsNameAndDescriptionScheme = new ArrayList<>();
        if (preparedNameAndDescriptionScheme.contains(" + ")) {
            medicamentsNameAndDescriptionScheme = Arrays.asList(preparedNameAndDescriptionScheme.split("(\\s*\\+\\s*)"));
        } else {
            medicamentsNameAndDescriptionScheme.add(preparedNameAndDescriptionScheme);
        }
        return medicamentsNameAndDescriptionScheme;
    }

    private static String removeCycleInEndStr(final String nameAndDescriptionScheme) {
        return nameAndDescriptionScheme.split(";\\s\\D*\\s\\d*\\s\\D*$")[0];
    }

    private static String removeIntravenouslyAndNBSP(final String nameAndDescriptionScheme) {
        return nameAndDescriptionScheme.replaceAll(" в/в ", " ").replaceAll(" ", " ");
    }

    private static String replaceCommasOnPointsInNumbers(String strWithCommas) {
        if (Pattern.compile("\\d+,\\d+").matcher(strWithCommas).find()) {
            strWithCommas = strWithCommas.replaceAll(",", ".");
        }
        return strWithCommas;
    }

}
