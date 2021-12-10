package com.novmik.tpc.costtreatment;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Стоимость одного случая госпитализации
 * по КСГ для случаев лекарственной
 * терапии взрослых со
 * злокачественными новообразованиями
 * control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/costtreatment")
@RestController
public class CostOfCompletedCaseOfTreatmentController {

  /**
   * {@link CostOfCompletedCaseOfTreatmentService}.
   */
  private final CostOfCompletedCaseOfTreatmentService ctService;

  /**
   * Стоимость госпитализации.
   * Post-запрос "api/v1/costtreatment"
   *
   * @param ctRequest {@link CostTreatmentRequest}
   * @return {@link CostTreatmentResponse}
   */
  @PostMapping
  public ResponseEntity<CostTreatmentResponse> getCostTreatment(
      @RequestBody final CostTreatmentRequest ctRequest) {
    return new ResponseEntity<>(
        ctService.getCostTreatmentWithDrg(ctRequest), OK);
  }

}
