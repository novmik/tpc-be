package com.novmik.tpc.costtreatment;

import static com.novmik.tpc.costtreatment.CostOfCompletedCaseOfTreatmentConstants.COEFFICIENT_SPECIFICITY_NOT_CORRECT;

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
      final float rateIntensity, final Integer typeMi
  ) {
    float coefficient = 0;
    if (typeMi == 1) {
      coefficient = rateIntensity >= 1.7 ? 1.4F : 1F;
    }
    if (typeMi == 2) {
      coefficient = rateIntensity >= 2 ? 1.4F : 1.2F;
    }
    if (typeMi == 3) {
      coefficient = rateIntensity >= 2 ? 1.4F : 1.0F;
    }
    if (typeMi == 4) {
      coefficient = rateIntensity >= 1.7 ? 1.4F : 1.0F;
    }
    if (typeMi == 5) {
      coefficient = rateIntensity >= 2 ? 1.4F : 0.8F;
    }
    if (coefficient == 0) {
      log.error(COEFFICIENT_SPECIFICITY_NOT_CORRECT, rateIntensity, typeMi);
    }
    return coefficient;
  }

  /**
   * Ctor.
   */
  private CoefficientSpecificityUtils() {
  }
}
