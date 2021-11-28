package com.novmik.tpc.costtreatment;

import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CostTreatmentResponse {
    private BigDecimal costTreatment;
    private DiagnosisRelatedGroups diagnosisRelatedGroups;
}
