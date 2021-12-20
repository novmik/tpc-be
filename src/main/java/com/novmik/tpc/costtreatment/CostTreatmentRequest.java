package com.novmik.tpc.costtreatment;

import com.novmik.tpc.cdt.CoefficientDifficultyTreating;
import com.novmik.tpc.medicalinstitution.MedicalInstitution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос стоимости одного
 * случая госпитализации
 * по КСГ для случаев лекарственной
 * терапии взрослых со
 * злокачественными новообразованиями.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CostTreatmentRequest {

  /**
   * id {@link MedicalInstitution}.
   */
  private Long idMi;
  /**
   * КСГ.
   */
  private String numberDrg;
  /**
   * Значение КСЛП {@link CoefficientDifficultyTreating}.
   */
  private Float valueCdt;
}
