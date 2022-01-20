package com.novmik.tpc.medicalinstitution;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.novmik.tpc.subject.Subject;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link MedicalInstitution} control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/mi")
@RestController
@SuppressWarnings("PMD.CommentSize")
public class MedicalInstitutionController {

  /**
   * {@link MedicalInstitutionService}.
   */
  private final MedicalInstitutionService miService;

  /**
   * Список id и наименование {@link MedicalInstitution}.
   * Get-запрос "api/v1/mi/{idSubject}/mil"
   * В теле ответа проекция {@link NameMedicalInstitutionAndId}
   *
   * @param idSubject id {@link Subject}
   * @return список {@link NameMedicalInstitutionAndId}
   */
  @GetMapping("/{idSubject}/mil")
  public ResponseEntity<List<NameMedicalInstitutionAndId>> getMedicalInstitutionList(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(miService
        .getMedicalInstitutionList(idSubject), OK);
  }

  /**
   * Список {@link MedicalInstitution}.
   * Get-запрос "api/v1/mi/{idSubject}/all"
   * В теле ответа список {@link MedicalInstitution},
   * сортированные по id
   *
   * @param idSubject id {@link Subject}
   * @return список {@link MedicalInstitution}
   */
  @GetMapping("/{idSubject}/all")
  public ResponseEntity<List<MedicalInstitution>> getAllMedicalInstitutionsBySubjectId(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(miService
        .getAllMedicalInstitutionsBySubjectId(idSubject), OK);
  }

  /**
   * Поиск {@link MedicalInstitution} по id.
   * Get-запрос "api/v1/mi/{idMi}"
   *
   * @param idMi id {@link MedicalInstitution}
   * @return {@link MedicalInstitution}
   */
  @GetMapping("/{idMedicalInstitution}")
  public ResponseEntity<MedicalInstitution> getMedicalInstitutionById(
      @PathVariable("idMedicalInstitution") final Long idMi) {
    return new ResponseEntity<>(miService
        .getMedicalInstitutionById(idMi), OK);
  }

  /**
   * Добавление {@link MedicalInstitution}.
   * Post-запрос "api/v1/mi"
   *
   * @param medInstitution {@link MedicalInstitution}
   * @return {@link MedicalInstitution}
   */
  @PostMapping
  public ResponseEntity<MedicalInstitution> addNewMedicalInstitution(
      @RequestBody final MedicalInstitution medInstitution) {
    return new ResponseEntity<>(miService
        .addNewMedicalInstitution(medInstitution), CREATED);
  }

  /**
   * Изменение {@link MedicalInstitution}.
   * Put-запрос "api/v1/mi"
   *
   * @param medInstitution {@link MedicalInstitution}
   * @return {@link MedicalInstitution}
   */
  @PutMapping
  public ResponseEntity<MedicalInstitution> updateMedicalInstitution(
      @RequestBody final MedicalInstitution medInstitution) {
    return new ResponseEntity<>(miService
        .updateMedicalInstitution(medInstitution), OK);
  }

  /**
   * Удаление {@link MedicalInstitution}.
   * Delete-запрос "api/v1/mi/{idMedicalInstitution}"
   * Доступ с полномочием 'DELETE'
   *
   * @param idMi id {@link MedicalInstitution}.
   */
  @DeleteMapping("/{idMedicalInstitution}")
  @PreAuthorize("hasAuthority('DELETE')")
  public void deleteMedicalInstitutionById(
      @PathVariable("idMedicalInstitution") final Long idMi) {
    miService
        .deleteMedicalInstitutionById(idMi);
  }
}
