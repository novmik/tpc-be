package com.novmik.tpc.medicament;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CheckMatcherPatternScheme {

  protected static boolean checkMinMaxDose(final String strWithDose) {
    return strWithDose != null && Pattern.compile("^\\d+,?\\d*-\\d+,?\\d*$")
        .matcher(strWithDose).find();
  }

  protected static boolean checkDose(final String strWithDose) {
    return strWithDose != null && Pattern.compile("^\\d+,?\\d*$")
        .matcher(strWithDose).find();
  }

  protected static boolean checkOnceEveryNxDays(final String strNumberDaysDrugTreatments) {
    return strNumberDaysDrugTreatments != null && Pattern.compile(" 1 раз в \\d+ дней")
        .matcher(strNumberDaysDrugTreatments).find();
  }
}
