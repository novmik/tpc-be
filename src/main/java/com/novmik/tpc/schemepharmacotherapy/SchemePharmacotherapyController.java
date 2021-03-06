package com.novmik.tpc.schemepharmacotherapy;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Схема лекарственной терапии control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/scheme")
@RestController
public class SchemePharmacotherapyController {

  /**
   * {@link SchemePharmacotherapyService}.
   */
  private final SchemePharmacotherapyService schemeService;

  /**
   * Список СЛТ.
   * Get-запрос "api/v1/scheme"
   *
   * @return список {@link SchemePharmacotherapy}
   */
  @GetMapping
  public ResponseEntity<List<SchemePharmacotherapy>> getAllSchemes() {
    return new ResponseEntity<>(schemeService.getAllSchemes(), OK);
  }

  /**
   * Поиск СЛТ по коду схемы.
   * Get-запрос "api/v1/scheme/{codeScheme}"
   *
   * @param codeScheme код СЛТ
   * @return СЛТ {@link SchemePharmacotherapy}
   */
  @GetMapping("/{codeScheme}")
  public ResponseEntity<SchemePharmacotherapy> getScheme(
      @PathVariable("codeScheme") final String codeScheme) {
    return new ResponseEntity<>(schemeService.findByCodeScheme(codeScheme), OK);
  }

  /**
   * Изменение {@link SchemePharmacotherapy}.
   * Put-запрос "api/v1/scheme"
   *
   * @param scheme {@link SchemePharmacotherapy}
   * @return {@link SchemePharmacotherapy}
   */
  @PutMapping
  @PreAuthorize("hasAuthority('WRITE')")
  public ResponseEntity<SchemePharmacotherapy> updateScheme(
      @RequestBody final SchemePharmacotherapy scheme) {
    return new ResponseEntity<>(schemeService.updateScheme(scheme), OK);
  }
}
