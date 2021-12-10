package com.novmik.tpc.costtreatment;

import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Ответ стоимость одного
 * случая госпитализации
 * по КСГ для случаев лекарственной
 * терапии взрослых со
 * злокачественными новообразованиями.
 */
@AllArgsConstructor
@Data
public class CostTreatmentResponse {

  /**
   * Стоимость лекарственной терапии.
   */
  private BigDecimal costTreatment;
  /**
   * {@link DiagnosisRelatedGroups}.
   */
  private DiagnosisRelatedGroups drg;
}
