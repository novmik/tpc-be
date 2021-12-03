package com.novmik.tpc.medicalinstitution;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1/mi")
@RestController
public class MedicalInstitutionController {

  private final MedicalInstitutionService medicalInstitutionService;

  @GetMapping("/{idSubject}/mil")
  public ResponseEntity<List<NameMedicalInstitutionAndId>> getMedicalInstitutionList(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(medicalInstitutionService
        .getMedicalInstitutionList(idSubject), OK);
  }

  @GetMapping("/{idSubject}/mis")
  public ResponseEntity<List<MedicalInstitution>> getAllMedicalInstitutionsBySubjectId(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(medicalInstitutionService
        .getAllMedicalInstitutionsBySubjectId(idSubject), OK);
  }

  @GetMapping("/{idMedicalInstitution}")
  public ResponseEntity<Optional<MedicalInstitution>> getMedicalInstitutionById(
      @PathVariable("idMedicalInstitution") final Long idMedicalInstitution) {
    return new ResponseEntity<>(medicalInstitutionService
        .getMedicalInstitutionById(idMedicalInstitution), OK);
  }

  @PostMapping
  public ResponseEntity<MedicalInstitution> addNewMedicalInstitution(
      @RequestBody final MedicalInstitution medicalInstitution) {
    return new ResponseEntity<>(medicalInstitutionService
        .addNewMedicalInstitution(medicalInstitution), CREATED);
  }

  @PutMapping
  public ResponseEntity<MedicalInstitution> updateMedicalInstitution(
      @RequestBody final MedicalInstitution medicalInstitution) {
    return new ResponseEntity<>(medicalInstitutionService
        .updateMedicalInstitution(medicalInstitution), OK);
  }

  @DeleteMapping("/{idMedicalInstitution}")
  public void deleteMedicalInstitutionById(
      @PathVariable("idMedicalInstitution") final Long idMedicalInstitution) {
    medicalInstitutionService
        .deleteMedicalInstitutionById(idMedicalInstitution);
  }
}
