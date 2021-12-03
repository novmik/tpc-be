package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPriceWithQuantityPackages;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CostSchemePharmacotherapyResponse {

  private BigDecimal costScheme;
  private List<Medicament> medicamentListWithRequiredDose;
  private List<MedicamentPriceWithQuantityPackages> listAllMedicamentPriceWithQuantityPackages;
}
