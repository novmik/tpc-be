package com.novmik.tpc.medicament;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Лекарственный препарат.
 * (ЛП)
 */
@Data
@AllArgsConstructor
public class Medicament {

  /**
   * МНН ЛП.
   */
  private String innMedicament;
  /**
   * Код СЛТ.
   * (схема лекарственной терапии).
   */
  private String numberCodeScheme;
  /**
   * Дозировка ЛП.
   */
  private Float dose;
  /**
   * Дозировка ЛП мин.
   */
  private Float doseMin;
  /**
   * Дозировка ЛП макс.
   */
  private Float doseMax;
  /**
   * Единица измерения дозировки.
   */
  private String unitOfMeasurement;
  /**
   * Кол-во дней введения ЛП.
   */
  private Integer numberDaysDrug;
  /**
   * Необходимая дозировка.
   */
  private BigDecimal requiredDose;
}
