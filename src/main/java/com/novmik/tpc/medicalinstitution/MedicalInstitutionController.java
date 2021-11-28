package com.novmik.tpc.medicalinstitution;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/mi")
@RestController
public class MedicalInstitutionController {

    private final MedicalInstitutionService medicalInstitutionService;

    @GetMapping("/{idSubject}/mil")
    public ResponseEntity<List<NameMedicalInstitutionAndId>> getMedicalInstitutionList(@PathVariable("idSubject") Long idSubject) {
        return new ResponseEntity<>(medicalInstitutionService.getMedicalInstitutionList(idSubject), OK);
    }

    @GetMapping("/{idSubject}/mis")
    public ResponseEntity<List<MedicalInstitution>> getAllMedicalInstitutionsBySubjectId(@PathVariable("idSubject") Long idSubject) {
        return new ResponseEntity<>(medicalInstitutionService.getAllMedicalInstitutionsBySubjectId(idSubject), OK);
    }

    @GetMapping("/{idMedicalInstitution}")
    public ResponseEntity<Optional<MedicalInstitution>> getMedicalInstitutionById(@PathVariable("idMedicalInstitution") Long idMedicalInstitution) {
        return new ResponseEntity<>(medicalInstitutionService.getMedicalInstitutionById(idMedicalInstitution), OK);
    }

    @PostMapping
    public ResponseEntity<MedicalInstitution> addNewMedicalInstitution(@RequestBody MedicalInstitution medicalInstitution) {
        return new ResponseEntity<>(medicalInstitutionService.addNewMedicalInstitution(medicalInstitution),CREATED);
    }

    @PutMapping
    public ResponseEntity<MedicalInstitution> updateMedicalInstitution(@RequestBody MedicalInstitution medicalInstitution) {
        return new ResponseEntity<>(medicalInstitutionService.updateMedicalInstitution(medicalInstitution),OK);
    }

    @DeleteMapping("/{idMedicalInstitution}")
    public void deleteMedicalInstitutionById(@PathVariable("idMedicalInstitution") Long idMedicalInstitution) {
        medicalInstitutionService.deleteMedicalInstitutionById(idMedicalInstitution);
    }
}
