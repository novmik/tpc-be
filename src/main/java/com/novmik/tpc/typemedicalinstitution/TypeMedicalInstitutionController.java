package com.novmik.tpc.typemedicalinstitution;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link TypeMedicalInstitution} control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/type_mo")
@RestController
public class TypeMedicalInstitutionController {

  /**
   * {@link TypeMedicalInstitutionService}.
   */
  private final TypeMedicalInstitutionService typeMiService;

  /**
   * Список типов МО.
   * Get-запрос "api/v1/type_mo"
   *
   * @return список {@link TypeMedicalInstitution}
   */
  @GetMapping()
  public ResponseEntity<List<TypeMedicalInstitution>> getAllTypeMedicalInstitution() {
    return new ResponseEntity<>(typeMiService.getAllTypeMedicalInstitution(), OK);
  }
}
