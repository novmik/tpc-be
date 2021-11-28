package com.novmik.tpc.medicament;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CheckMatcherPatternScheme {

    boolean checkMinMaxDose(String strWithDose) {
        return strWithDose != null && Pattern.compile("^\\d+,?\\d*-\\d+,?\\d*$")
                .matcher(strWithDose).find();
    }

    boolean checkDose(String strWithDose) {
        return strWithDose != null && Pattern.compile("^\\d+,?\\d*$")
                .matcher(strWithDose).find();
    }

    boolean checkOnceEveryNxDays(String strNumberDaysDrugTreatments) {
        return strNumberDaysDrugTreatments != null && Pattern.compile(" 1 раз в \\d+ дней")
                .matcher(strNumberDaysDrugTreatments).find();
    }
}
