package com.novmik.tpc.costtreatment;

import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CostTreatmentResponse {

  private BigDecimal costTreatment;
  private DiagnosisRelatedGroups diagnosisRelatedGroups;
}
