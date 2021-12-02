package com.novmik.tpc.drg;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/drg")
@RestController
public class DiagnosisRelatedGroupsController {

    private final DiagnosisRelatedGroupsService drgService;

    @GetMapping("/{drg}")
    public ResponseEntity<Optional<DiagnosisRelatedGroups>> getDiagnosisRelatedGroups(@PathVariable("drg") final String drg) {
        return new ResponseEntity<>(drgService.byNumberDrg(drg), OK);
    }

    @PostMapping
    public ResponseEntity<DiagnosisRelatedGroups> addNewDrg(@RequestBody final DiagnosisRelatedGroups diagnosisRelatedGroups) {
        return new ResponseEntity<>(drgService.addNewDrg(diagnosisRelatedGroups), CREATED);
    }

    @PutMapping
    public ResponseEntity<DiagnosisRelatedGroups> updateDrg(@RequestBody final DiagnosisRelatedGroups diagnosisRelatedGroups) {
        return new ResponseEntity<>(drgService.updateDrg(diagnosisRelatedGroups), OK);
    }

    @DeleteMapping("/{idDrg}")
    public void deleteDrgById(@PathVariable("idDrg") final Long idDrg) {
        drgService.deleteDrgById(idDrg);
    }


}
