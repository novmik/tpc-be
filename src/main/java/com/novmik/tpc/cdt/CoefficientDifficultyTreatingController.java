package com.novmik.tpc.cdt;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.novmik.tpc.cdt.CdtConstant.DAY_CARE_FACILITY;
import static com.novmik.tpc.cdt.CdtConstant.ROUND_THE_CLOCK_CARE_FACILITY;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/cdt")
@RestController
public class CoefficientDifficultyTreatingController {

    private final CoefficientDifficultyTreatingService cdtService;

    @GetMapping("/{idSubject}/" + ROUND_THE_CLOCK_CARE_FACILITY + "/")
    public ResponseEntity<List<CoefficientDifficultyTreating>> getRoundTheClockCareFacilityCdtpListBySubjectId(@PathVariable("idSubject") Long idSubject) {
        return new ResponseEntity<>(cdtService.getCareFacilityCdtpListBySubjectId(idSubject, ROUND_THE_CLOCK_CARE_FACILITY), OK);
    }

    @GetMapping("/{idSubject}/" + DAY_CARE_FACILITY + "/")
    public ResponseEntity<List<CoefficientDifficultyTreating>> getDayCareFacilityCdtpListBySubjectId(@PathVariable("idSubject") Long idSubject) {
        return new ResponseEntity<>(cdtService.getCareFacilityCdtpListBySubjectId(idSubject, DAY_CARE_FACILITY), OK);
    }

    @PostMapping()
    public ResponseEntity<CoefficientDifficultyTreating> addNewCDTP(@RequestBody CoefficientDifficultyTreating cdt) {
        return new ResponseEntity<>(cdtService.addNewCoefficientDifficultyTreating(cdt), OK);
    }
}
