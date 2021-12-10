package com.novmik.tpc.costscheme;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Стоимость СЛТ
 * (Схема лекарственной
 * терапии) control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/costscheme")
@RestController
public class CostSchemePharmacotherapyController {

  /**
   * {@link CostSchemePharmacotherapyService}.
   */
  private CostSchemePharmacotherapyService costSchemeService;

  /**
   * Стоимость СЛТ.
   * Post-запрос "api/v1/costscheme"
   *
   * @param costSchemeRequest {@link CostSchemePharmacotherapyRequest}
   * @return {@link CostSchemePharmacotherapyResponse}
   */
  @PostMapping
  public ResponseEntity<CostSchemePharmacotherapyResponse> getCostSchemePharmacotherapyByCodeScheme(
      @RequestBody final CostSchemePharmacotherapyRequest costSchemeRequest
  ) {
    return new ResponseEntity<>(
        costSchemeService.getCostSchemePharmacotherapy(costSchemeRequest), OK);
  }
}
