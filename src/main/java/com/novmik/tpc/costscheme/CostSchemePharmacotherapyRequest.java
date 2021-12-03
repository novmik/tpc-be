package com.novmik.tpc.costscheme;

import com.novmik.tpc.medicament.Medicament;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CostSchemePharmacotherapyRequest {

  private String codeScheme;
  private List<Medicament> medicamentList;
  private Double regionalMarkup;
  private Double weight;
  private Double bsa;
}
