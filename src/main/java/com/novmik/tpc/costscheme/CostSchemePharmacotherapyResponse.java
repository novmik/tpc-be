package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPriceWithQuantityPackages;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
public class CostSchemePharmacotherapyResponse {

    private BigDecimal costScheme;
    private List<Medicament> medicamentListWithRequiredDose;
    private List<MedicamentPriceWithQuantityPackages> listAllMedicamentPriceWithQuantityPackages;
}
