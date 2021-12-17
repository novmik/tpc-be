package com.novmik.tpc.costscheme;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 * Калькулятор остатка ЛП.
 */
@Component
@SuppressWarnings({"PMD.CommentSize", "PMD.UseConcurrentHashMap", "DataflowAnomalyAnalysis"})
public final class CalculatorResidualUtils {

  /**
   * {@link SortedMap} Остаток ЛП ->
   * {Необходимая дозировка -> Кол-во упаковок}.
   *
   * @param dosagesMeds    список доступных дозировок
   *                       sort от max к min
   * @param bdRequiredDose необходимая дозировка
   * @return {@link SortedMap}
   */
  public static SortedMap<Double, Map<Float, Integer>> getMapResidual(
      final List<Float> dosagesMeds,
      final BigDecimal bdRequiredDose) {
    final SortedMap<Double, Map<Float, Integer>> mapResidual = new TreeMap<>();
    Map<Float, Integer> tempMap;
    final Map<Float, Integer> dosePackage = new HashMap<>();
    double requiredDose = bdRequiredDose.doubleValue();
    for (final Float dosage : dosagesMeds) {
      if (requiredDose >= dosage) {
        final int quantityPackage = (int) (requiredDose / dosage);
        dosePackage.merge(dosage, quantityPackage, Integer::sum);
        requiredDose %= dosage;
        if (requiredDose == 0) {
          mapResidual.put(requiredDose, dosePackage);
          break;
        }
      }
      final double fissionExcess = dosage - requiredDose;
      final int quantityPackage = (int) Math.ceil(requiredDose / dosage);
      tempMap = new HashMap<>(dosePackage);
      tempMap.merge(dosage, quantityPackage, Integer::sum);
      mapResidual.put(fissionExcess, tempMap);
    }
    return mapResidual;
  }

  private CalculatorResidualUtils() {
  }
}
