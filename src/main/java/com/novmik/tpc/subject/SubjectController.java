package com.novmik.tpc.subject;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
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
 * {@link Subject} control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/s")
@RestController
public class SubjectController {

  /**
   * {@link SubjectService}.
   */
  private final SubjectService subjectService;

  /**
   * Список id и наименование {@link Subject}.
   * Get-запрос "api/v1/s"
   * В теле ответа проекция {@link NameSubjectAndId}
   *
   * @return список {@link NameSubjectAndId}
   */
  @GetMapping
  public ResponseEntity<List<NameSubjectAndId>> getIdAndNameSubjectFromTable() {
    return new ResponseEntity<>(subjectService.getIdAndNameSubjectFromTable(), OK);
  }

  /**
   * Список {@link Subject}.
   * Get-запрос "api/v1/s/all"
   * В теле ответа список {@link Subject},
   * сортированные по id
   *
   * @return список {@link Subject}
   */
  @GetMapping("/all")
  public ResponseEntity<List<Subject>> getAllSubjects() {
    return new ResponseEntity<>(subjectService.getAllSubject(), OK);
  }

  /**
   * Поиск {@link Subject} по id.
   * Get-запрос "api/v1/s/{idSubject}"
   *
   * @param idSubject id {@link Subject}
   * @return {@link Subject}
   */
  @GetMapping("/{idSubject}")
  public ResponseEntity<Subject> getSubjectById(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(subjectService.getSubjectById(idSubject), OK);
  }

  /**
   * Добавление {@link Subject}.
   * Post-запрос "api/v1/s"
   *
   * @param subject {@link Subject} без id
   * @return {@link Subject}
   */
  @PostMapping
  public ResponseEntity<Subject> addNewSubject(@RequestBody final Subject subject) {
    return new ResponseEntity<>(subjectService.addNewSubject(subject), CREATED);
  }

  /**
   * Изменение {@link Subject}.
   * Put-запрос "api/v1/s"
   *
   * @param subject {@link Subject}
   * @return {@link Subject}
   */
  @PutMapping
  public ResponseEntity<Subject> updateSubject(@RequestBody final Subject subject) {
    return new ResponseEntity<>(subjectService.updateSubject(subject), OK);
  }

  /**
   * Удаление {@link Subject}.
   * Delete-запрос "api/v1/s/{idSubject}"
   * Доступ с полномочием 'DELETE'
   *
   * @param idSubject id {@link Subject}
   */
  @DeleteMapping("/{idSubject}")
  @PreAuthorize("hasAuthority('DELETE')")
  public void deleteSubjectById(@PathVariable("idSubject") final Long idSubject) {
    subjectService.deleteSubjectById(idSubject);
  }
}
