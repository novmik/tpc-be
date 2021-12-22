package com.novmik.tpc.cdt;

import static com.novmik.tpc.cdt.CoefficientDifficultyTreatingService.DAY_CARE_FACILITY;
import static com.novmik.tpc.cdt.CoefficientDifficultyTreatingService.ROUND_THE_CLOCK_CARE_FACILITY;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * КСЛП control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/cdt")
@RestController
public class CoefficientDifficultyTreatingController {

  /**
   * КСЛП-сервис {@link CoefficientDifficultyTreatingService}.
   */
  private final CoefficientDifficultyTreatingService cdtService;

  /**
   * Список КСЛП КС.
   * Get-запрос "api/v1/cdt/{idSubject}/st"
   *
   * @param idSubject id субъекта РФ
   * @return список КСЛП {@link CoefficientDifficultyTreating}
   */
  @GetMapping("/{idSubject}/" + ROUND_THE_CLOCK_CARE_FACILITY)
  public ResponseEntity
      <List<CoefficientDifficultyTreating>> getStCdtpListBySubjectId(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(
        cdtService.getCareFacilityCdtListBySubjectId(idSubject, ROUND_THE_CLOCK_CARE_FACILITY),
        OK);
  }

  /**
   * Список КСЛП ДС.
   * Get-запрос "api/v1/cdt/{idSubject}/ds"
   *
   * @param idSubject id субъекта РФ
   * @return список КСЛП {@link CoefficientDifficultyTreating}
   */
  @GetMapping("/{idSubject}/" + DAY_CARE_FACILITY)
  public ResponseEntity
      <List<CoefficientDifficultyTreating>> getDsCdtpListBySubjectId(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(
        cdtService.getCareFacilityCdtListBySubjectId(idSubject, DAY_CARE_FACILITY),
        OK);
  }

  /**
   * Добавление КСЛП.
   * Post-запрос "api/v1/cdt"
   *
   * @param cdt КСЛП {@link CoefficientDifficultyTreating}
   * @return КСЛП {@link CoefficientDifficultyTreating}
   */
  @PostMapping()
  public ResponseEntity
      <CoefficientDifficultyTreating> addNewCdtp(
      @RequestBody final CoefficientDifficultyTreating cdt) {
    return new ResponseEntity<>(cdtService.addNewCoefficientDifficultyTreating(cdt), CREATED);
  }
}
