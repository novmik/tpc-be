package com.novmik.tpc.subject;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
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

@AllArgsConstructor
@RequestMapping("api/v1/s")
@RestController
public class SubjectController {

  private final SubjectService subjectService;

  @GetMapping
  public ResponseEntity<List<NameSubjectAndId>> getIdAndNameSubjectFromTable() {
    return new ResponseEntity<>(subjectService.getIdAndNameSubjectFromTable(), OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Subject>> getAllSubjects() {
    return new ResponseEntity<>(subjectService.getAllSubject(), OK);
  }

  @GetMapping("/{idSubject}")
  public ResponseEntity<Optional<Subject>> getSubjectById(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(subjectService.getSubjectById(idSubject), OK);
  }

  @PostMapping
  public ResponseEntity<Subject> addNewSubject(@RequestBody final Subject subject) {
    return new ResponseEntity<>(subjectService.addNewSubject(subject), CREATED);
  }

  @PutMapping
  public ResponseEntity<Subject> updateSubject(@RequestBody final Subject subject) {
    return new ResponseEntity<>(subjectService.updateSubject(subject), OK);
  }

  @DeleteMapping("/{idSubject}")
  @PreAuthorize("hasAuthority('DELETE')")
  public void deleteSubjectById(@PathVariable("idSubject") final Long idSubject) {
    subjectService.deleteSubjectById(idSubject);
  }
}
