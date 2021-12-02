package com.novmik.tpc.costscheme;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/*
    Калькулятор Остатка лекарства
    Получает лист возможных дозировок лекарства и необходимую дозировку
    SortedMap: Остаток лекарства -> { Необходимая дозировка лекарства -> Кол-во упаковок лекарства }
 */

@Component
public class CalculatorResidual {

    protected static SortedMap<Double, Map<Float, Integer>> getMapResidual(final List<Float> dosagesMedicamentList,
                                                                 final BigDecimal requiredDoseMedicament) {

        SortedMap<Double, Map<Float, Integer>> mapResidual = new TreeMap<>();
        Map<Float, Integer> mapDoseQuantityPackage = new HashMap<>();
        double requiredDose = requiredDoseMedicament.doubleValue();
        for (Float dosage : dosagesMedicamentList) {
            if (requiredDose >= dosage) {
                int quantityPackage = (int) (requiredDose / dosage);
                if (mapDoseQuantityPackage.containsKey(dosage)) {
                    mapDoseQuantityPackage.replace(dosage, (mapDoseQuantityPackage.get(dosage) + quantityPackage));
                } else {
                    mapDoseQuantityPackage.put(dosage, quantityPackage);
                }
                requiredDose %= dosage;
                if (requiredDose == 0) {
                    mapResidual.put(requiredDose, mapDoseQuantityPackage);
                    break;
                }
            }
            double fissionExcess = dosage - requiredDose;
            int quantityPackage = (int) Math.ceil(requiredDose / dosage);
            Map<Float, Integer> tempMapDoseQuantityPackage = new HashMap<>(mapDoseQuantityPackage);
            if (tempMapDoseQuantityPackage.containsKey(dosage)) {
                tempMapDoseQuantityPackage.replace(dosage, (tempMapDoseQuantityPackage.get(dosage) + quantityPackage));
            } else {
                tempMapDoseQuantityPackage.put(dosage, quantityPackage);
            }
            mapResidual.put(fissionExcess, tempMapDoseQuantityPackage);
        }
        return mapResidual;
    }
}
