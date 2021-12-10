package com.novmik.tpc.costscheme;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Калькулятор остатка ЛП.
 */
@Component
public final class CalculatorResidualUtils {

  /**
   * {@link SortedMap} Остаток лекарства ->
   * {Необходимая дозировка -> Кол-во упаковок}.
   *
   * @param dosagesMeds  список доступных дозировок
   * @param bdRequiredDose необходимая дозировка
   * @return {@link SortedMap}
   */
  public static SortedMap<Double, Map<Float, Integer>> getMapResidual(
      final List<Float> dosagesMeds,
      final BigDecimal bdRequiredDose) {
    final SortedMap<Double, Map<Float, Integer>> mapResidual = new TreeMap<>();
    final Map<Float, Integer> dosePackage = new ConcurrentHashMap<>();
    double requiredDose = bdRequiredDose.doubleValue();
    for (final Float dosage : dosagesMeds) {
      if (requiredDose >= dosage) {
        final int quantityPackage = (int) (requiredDose / dosage);
        if (dosePackage.containsKey(dosage)) {
          dosePackage.replace(dosage,
              dosePackage.get(dosage) + quantityPackage);
        } else {
          dosePackage.put(dosage, quantityPackage);
        }
        requiredDose %= dosage;
        if (requiredDose == 0) {
          mapResidual.put(requiredDose, dosePackage);
          break;
        }
      }
      final double fissionExcess = dosage - requiredDose;
      final int quantityPackage = (int) Math.ceil(requiredDose / dosage);
      final Map<Float, Integer> tempMap =
          new ConcurrentHashMap<>(dosePackage);
      if (tempMap.containsKey(dosage)) {
        tempMap.replace(dosage,
            tempMap.get(dosage) + quantityPackage);
      } else {
        tempMap.put(dosage, quantityPackage);
      }
      mapResidual.put(fissionExcess, tempMap);
    }
    return mapResidual;
  }

  private CalculatorResidualUtils() {
  }
}
