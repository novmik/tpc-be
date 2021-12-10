package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPriceWithPackages;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Ответ стоимость СЛТ.
 * (Схема лекарственной терапии)
 */
@AllArgsConstructor
@Data
public class CostSchemePharmacotherapyResponse {

  /**
   * Стоимость СЛТ.
   */
  private BigDecimal costScheme;
  /**
   * Список {@link Medicament}.
   * С необходимой дозировкой.
   */
  private List<Medicament> medicaments;
  /**
   * Список {@link MedicamentPriceWithPackages}.
   */
  private List<MedicamentPriceWithPackages> medicamentsPrice;
}
