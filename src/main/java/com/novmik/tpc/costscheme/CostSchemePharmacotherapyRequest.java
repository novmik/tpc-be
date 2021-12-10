package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Запрос стоимости СЛТ.
 * (Схема лекарственной терапии)
 */
@AllArgsConstructor
@Data
public class CostSchemePharmacotherapyRequest {

  /**
   * Код СЛТ.
   * (схема лекарственной терапии)
   */
  private String codeScheme;
  /**
   * Список {@link Medicament}.
   */
  private List<Medicament> medicamentList;
  /**
   * Региональная наценка на ЛП.
   */
  private Double regionalMarkup;
  /**
   * Вес человека, кг.
   */
  private Double weight;
  /**
   * ППТ {@link com.novmik.tpc.bsa.BodySurfaceAreaUtils}.
   */
  private Double bsa;
}
