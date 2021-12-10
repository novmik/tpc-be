package com.novmik.tpc.bsa;

/**
 * Constants для пакета bsa.
 */
@SuppressWarnings("PMD.LongVariable")
public class BodySurfaceAreaConstants {

  /**
   * BSA_REQUEST_NOT_CORRECT.
   */
  public static final String BSA_REQUEST_NOT_CORRECT =
      "Некорректные параметры(рост или вес): ";
  /**
   * BSA_NOT_CORRECT_2_OPTIONS.
   */
  public static final String BSA_NOT_CORRECT_2_OPTIONS =
      "Некорректные параметры(рост или вес) ППТ: ";
  /**
   * BSA_NOT_CORRECT_1_OPTION.
   */
  public static final String BSA_NOT_CORRECT_1_OPTION =
      "Некорректный параметр(вес) ППТ: ";
  /**
   * MIN_HEIGHT.
   */
  public static final int MIN_HEIGHT = 55;
  /**
   * MAX_HEIGHT.
   */
  public static final int MAX_HEIGHT = 500;
  /**
   * MIN_WEIGHT.
   */
  public static final int MIN_WEIGHT = 10;
  /**
   * MAX_WEIGHT.
   */
  public static final int MAX_WEIGHT = 1000;
}
