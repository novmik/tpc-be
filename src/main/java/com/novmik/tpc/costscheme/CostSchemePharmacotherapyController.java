package com.novmik.tpc.costscheme;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1/costscheme")
@RestController
public class CostSchemePharmacotherapyController {

  private CostSchemePharmacotherapyService costSchemeService;

  @PostMapping
  public ResponseEntity<CostSchemePharmacotherapyResponse> getCostSchemePharmacotherapyByCodeScheme(
      @RequestBody final CostSchemePharmacotherapyRequest costSchemeRequest) {
    return new ResponseEntity<>(costSchemeService.getCostSchemePharmacotherapy(costSchemeRequest),
        OK);
  }
}
