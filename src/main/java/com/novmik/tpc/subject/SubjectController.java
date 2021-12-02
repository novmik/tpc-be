package com.novmik.tpc.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/s")
@RestController
public class SubjectController {

    private final SubjectOfRFService subjectOfRFService;

    @GetMapping
    public ResponseEntity<List<NameSubjectAndId>> getIdAndNameSubjectFromTable() {
        return new ResponseEntity<>(subjectOfRFService.getIdAndNameSubjectFromTable(), OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectOfRF>> getAllSubjects() {
        return new ResponseEntity<>(subjectOfRFService.getAllSubject(), OK);
    }

    @GetMapping("/{idSubject}")
    public ResponseEntity<Optional<SubjectOfRF>> getSubjectById(@PathVariable("idSubject") final Long idSubject) {
        return new ResponseEntity<>(subjectOfRFService.getSubjectById(idSubject), OK);
    }

    @PostMapping
    public ResponseEntity<SubjectOfRF> addNewSubject(@RequestBody final SubjectOfRF subjectOfRF) {
        return new ResponseEntity<>(subjectOfRFService.addNewSubject(subjectOfRF), CREATED);
    }

    @PutMapping
    public ResponseEntity<SubjectOfRF> updateSubject(@RequestBody final SubjectOfRF subjectOfRF) {
        return new ResponseEntity<>(subjectOfRFService.updateSubject(subjectOfRF), OK);
    }

    @DeleteMapping("/{idSubject}")
    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteSubjectById(@PathVariable("idSubject") final Long idSubject) {
        subjectOfRFService.deleteSubjectById(idSubject);
    }
}
