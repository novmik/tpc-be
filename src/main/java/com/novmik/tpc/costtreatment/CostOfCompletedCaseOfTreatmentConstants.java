package com.novmik.tpc.costtreatment;

/**
 * Constants для пакета costtreatment.
 */
@SuppressWarnings("PMD.LongVariable")
public class CostOfCompletedCaseOfTreatmentConstants {

  /**
   * SUBJECT_ID_NUMBER_DRG_VALUE_CDT.
   */
  public static final String SUBJECT_ID_NUMBER_DRG_VALUE_CDT =
      "Субъект id/Номер КСГ/Значение КСГ указаны неверно: ";
  /**
   * NUMBER_DRG_INCORRECT.
   */
  public static final String NUMBER_DRG_INCORRECT = "Номер КСГ не верный: ";
  /**
   * VALUES_IN_TABLE_IS_NOT_EXISTS.
   */
  public static final String VALUES_IN_TABLE_IS_NOT_EXISTS =
      "Значения БС/КУС в таблице отсутствуют: ";
  /**
   * DEFAULT_VALUE_CDT.
   */
  public static final float DEFAULT_VALUE_CDT = 1F;
  /**
   * COEFFICIENT_SPECIFICITY_NOT_FEDERAL.
   */
  public static final float COEFFICIENT_SPECIFICITY_NOT_FEDERAL = 1F;
  /**
   * COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY.
   */
  public static final double COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY = 0.41;
  /**
   * COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY.
   */
  public static final double COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY = 0.52;
  /**
   * FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY.
   */
  public static final double FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY = 56_680.90;
  /**
   * FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY.
   */
  public static final double FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY = 25_617.30;
  /**
   * COEFFICIENT_SPECIFICITY_NOT_CORRECT.
   */
  public static final String COEFFICIENT_SPECIFICITY_NOT_CORRECT =
      "Коэффициент специфики не определен, со следующими параметрами КЗ: {},Типом МО: {}";
  /**
   * COEFFICIENT_SPECIFICITY_IS_ZERO.
   */
  public static final String COEFFICIENT_SPECIFICITY_IS_ZERO =
      "Значение Коэффициента специфики не определено";

}
