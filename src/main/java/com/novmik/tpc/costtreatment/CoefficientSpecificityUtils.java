package com.novmik.tpc.costtreatment;

import com.novmik.tpc.typemedicalinstitution.TypeMedicalInstitution;
import lombok.extern.slf4j.Slf4j;

/**
 * КС.
 * (Коэффициент специфики)
 */
@Slf4j
public final class CoefficientSpecificityUtils {

  /**
   * Вычисление КС.
   * В зависимости от Кз и типа МО
   *
   * @param rateIntensity Кз
   * @param typeMi        {@link TypeMedicalInstitution}
   * @return коэффициент специфики
   */
  public static float calculate(
      final float rateIntensity,
      final Integer typeMi
  ) {
    float coefficient = 0;
    switch (typeMi) {
      case 1 -> coefficient = rateIntensity >= 1.7 ? 1.4F : 1F;
      case 2 -> coefficient = rateIntensity >= 2 ? 1.4F : 1.2F;
      case 3 -> coefficient = rateIntensity >= 2 ? 1.4F : 1.0F;
      case 4 -> coefficient = rateIntensity >= 1.7 ? 1.4F : 1.0F;
      case 5 -> coefficient = rateIntensity >= 2 ? 1.4F : 0.8F;
      default -> log.error(
          "Коэффициент специфики не определен, со следующими параметрами КЗ: {}, Типом МО: {}",
          rateIntensity, typeMi);
    }
    return coefficient;
  }

  /**
   * Ctor.
   */
  private CoefficientSpecificityUtils() {
  }
}
