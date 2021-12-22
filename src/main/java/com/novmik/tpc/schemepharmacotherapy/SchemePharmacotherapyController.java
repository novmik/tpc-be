package com.novmik.tpc.schemepharmacotherapy;

import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Схема лекарственной терапии control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/codescheme")
@RestController
public class SchemePharmacotherapyController {

  /**
   * SchemePharmacotherapyService {@link SchemePharmacotherapyService}.
   */
  private final SchemePharmacotherapyService spService;

  /**
   * Поиск СЛТ по коду схемы.
   * Get-запрос "api/v1/codescheme/{codeScheme}"
   *
   * @param codeScheme код СЛТ
   * @return СЛТ {@link SchemePharmacotherapy}
   */
  @GetMapping("/{codeScheme}")
  public ResponseEntity<SchemePharmacotherapy> getSchemePharmacotherapy(
      @PathVariable("codeScheme") final String codeScheme) {
    return new ResponseEntity<>(spService.findByCodeScheme(codeScheme), OK);
  }
}
