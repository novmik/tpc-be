package com.novmik.tpc.typemedicalinstitution;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1/type_mo")
@RestController
public class TypeMedicalInstitutionController {

  private final TypeMedicalInstitutionService typeMedicalInstitutionService;

  @GetMapping()
  public ResponseEntity<List<TypeMedicalInstitution>> getAllTypeMedicalInstitution() {
    return new ResponseEntity<>(typeMedicalInstitutionService.getAllTypeMedicalInstitution(), OK);
  }
}
