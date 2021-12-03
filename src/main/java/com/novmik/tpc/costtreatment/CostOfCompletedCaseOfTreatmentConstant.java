package com.novmik.tpc.costtreatment;

public class CostOfCompletedCaseOfTreatmentConstant {

  public static final String SUBJECT_ID_NUMBER_DRG_VALUE_CDT =
      "Субъект id/Номер КСГ/Значение КСГ указаны неверно: ";
  public static final String NUMBER_DRG_INCORRECT = "Номер КСГ не верный: ";
  public static final String VALUES_IN_TABLE_IS_NOT_EXISTS =
      "Значения БС/КУС в таблице отсутствуют: ";
  public static final float DEFAULT_VALUE_CDT = 1F;
  public static final float COEFFICIENT_SPECIFICITY_NOT_FEDERAL = 1F;
  public static final double COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY = 0.41;
  public static final double COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY = 0.52;
  public static final double FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY = 56_680.90;
  public static final double FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY = 25_617.30;
  public static final String COEFFICIENT_SPECIFICITY_NOT_CORRECT =
      "Коэффициент специфики не определен, со следующими параметрами КЗ: {},Типом МО: {}";
  public static final String COEFFICIENT_SPECIFICITY_IS_ZERO =
      "Значение Коэффициента специфики не определено";

}
