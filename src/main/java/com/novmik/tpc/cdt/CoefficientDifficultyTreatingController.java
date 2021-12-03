package com.novmik.tpc.cdt;

import static com.novmik.tpc.cdt.CdtConstant.DAY_CARE_FACILITY;
import static com.novmik.tpc.cdt.CdtConstant.ROUND_THE_CLOCK_CARE_FACILITY;
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


@AllArgsConstructor
@RequestMapping("api/v1/cdt")
@RestController
public class CoefficientDifficultyTreatingController {

  private final CoefficientDifficultyTreatingService cdtService;

  @GetMapping("/{idSubject}/" + ROUND_THE_CLOCK_CARE_FACILITY + "/")
  public ResponseEntity
      <List<CoefficientDifficultyTreating>> getRoundTheClockCareFacilityCdtpListBySubjectId(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(
        cdtService.getCareFacilityCdtpListBySubjectId(idSubject, ROUND_THE_CLOCK_CARE_FACILITY),
        OK);
  }

  @GetMapping("/{idSubject}/" + DAY_CARE_FACILITY + "/")
  public ResponseEntity
      <List<CoefficientDifficultyTreating>> getDayCareFacilityCdtpListBySubjectId(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(
        cdtService.getCareFacilityCdtpListBySubjectId(idSubject, DAY_CARE_FACILITY), OK);
  }

  @PostMapping()
  public ResponseEntity
      <CoefficientDifficultyTreating> addNewCdtp(
      @RequestBody final CoefficientDifficultyTreating cdt) {
    return new ResponseEntity<>(cdtService.addNewCoefficientDifficultyTreating(cdt), OK);
  }
}
