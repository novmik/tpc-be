package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CostSchemePharmacotherapyRequest {
    private String codeScheme;
    private List<Medicament> medicamentList;
    private Double regionalMarkup;
    private Double weight;
    private Double bsa;
}
