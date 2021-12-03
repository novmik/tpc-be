package com.novmik.tpc.costtreatment;

import lombok.Data;

@Data
public class CostTreatmentRequest {

  private Long idMedicalInstitution;
  private String numberDrg;
  private Float valueCdt;
}
