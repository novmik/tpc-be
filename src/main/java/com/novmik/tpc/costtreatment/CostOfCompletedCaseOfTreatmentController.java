package com.novmik.tpc.costtreatment;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/costtreatment")
@RestController
public class CostOfCompletedCaseOfTreatmentController {

    private final CostOfCompletedCaseOfTreatmentService costTreatmentService;

    @PostMapping
    public ResponseEntity<CostTreatmentResponse> getCostTreatment(@RequestBody CostTreatmentRequest ctRequest) {
        return new ResponseEntity<>(costTreatmentService.getCostTreatmentWithDrg(ctRequest), OK);
    }

}
