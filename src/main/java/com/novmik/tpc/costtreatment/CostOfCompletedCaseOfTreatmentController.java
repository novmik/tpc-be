package com.novmik.tpc.costtreatment;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1/costtreatment")
@RestController
public class CostOfCompletedCaseOfTreatmentController {

  private final CostOfCompletedCaseOfTreatmentService costTreatmentService;

  @PostMapping
  public ResponseEntity<CostTreatmentResponse> getCostTreatment(
      @RequestBody final CostTreatmentRequest ctRequest) {
    return new ResponseEntity<>(costTreatmentService.getCostTreatmentWithDrg(ctRequest), OK);
  }

}
