package com.novmik.tpc.drg;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link DiagnosisRelatedGroups} control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/drg")
@RestController
public class DiagnosisRelatedGroupsController {

  /**
   * {@link DiagnosisRelatedGroupsService}.
   */
  private final DiagnosisRelatedGroupsService drgService;

  /**
   * Поиск {@link DiagnosisRelatedGroups}.
   * Get-запрос "api/v1/drg/{drg}"
   *
   * @param drg КСГ
   * @return {@link DiagnosisRelatedGroups}
   */
  @GetMapping("/{drg}")
  public ResponseEntity<Optional<DiagnosisRelatedGroups>> getDiagnosisRelatedGroups(
      @PathVariable("drg") final String drg) {
    return new ResponseEntity<>(drgService.byNumberDrg(drg), OK);
  }

  /**
   * Добавление {@link DiagnosisRelatedGroups}.
   * Post-запрос "api/v1/drg"
   *
   * @param drg {@link DiagnosisRelatedGroups} без id
   * @return {@link DiagnosisRelatedGroups}
   */
  @PostMapping
  public ResponseEntity<DiagnosisRelatedGroups> addNewDrg(
      @RequestBody final DiagnosisRelatedGroups drg) {
    return new ResponseEntity<>(drgService.addNewDrg(drg), CREATED);
  }

  /**
   * Изменение {@link DiagnosisRelatedGroups}.
   * Put-запрос "api/v1/drg"
   *
   * @param drg {@link DiagnosisRelatedGroups}
   * @return {@link DiagnosisRelatedGroups}
   */
  @PutMapping
  public ResponseEntity<DiagnosisRelatedGroups> updateDrg(
      @RequestBody final DiagnosisRelatedGroups drg) {
    return new ResponseEntity<>(drgService.updateDrg(drg), OK);
  }

  /**
   * Удаление {@link DiagnosisRelatedGroups}.
   * Delete-запрос "api/v1/drg/{idDrg}"
   *
   * @param idDrg id {@link DiagnosisRelatedGroups}
   */
  @DeleteMapping("/{idDrg}")
  @PreAuthorize("hasAuthority('DELETE')")
  public void deleteDrgById(@PathVariable("idDrg") final Long idDrg) {
    drgService.deleteDrgById(idDrg);
  }

}
