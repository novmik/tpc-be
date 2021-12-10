package com.novmik.tpc.subject;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.IntegrationTestBase;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SubjectRepositoryTest extends IntegrationTestBase {

  @Autowired
  private SubjectRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void canGetIdAndNameSubjectListFromTable() {
    Subject subject = new Subject(
        "Test SubjectName",
        1.00D,
        1.00D);
    subject = underTest.save(subject);
    List<NameSubjectAndId> idAndNameSubjectFromTable = underTest.getIdAndNameSubjectFromTable();
    assertThat(subject.getIdSubject()).isNotNull();
    assertThat(idAndNameSubjectFromTable.size()).isEqualTo(1);
  }

  @Test
  void canFindByNameSubject() {
    Subject subject = new Subject(
        "Test SubjectName",
        1.00D,
        1.00D);
    subject = underTest.save(subject);
    Subject actualSubject = underTest.findByNameSubject(subject.getNameSubject()).orElse(null);
    assertThat(subject).hasNoNullFieldsOrProperties();
    assertThat(subject).isEqualTo(actualSubject);
  }

  @Test
  void notFindByNameSubject() {
    Subject subject = new Subject(
        "Test SubjectName",
        1.00D,
        1.00D);
    underTest.save(subject);
    Optional<Subject> byNameSubject = underTest.findByNameSubject("test");
    assertThat(byNameSubject).isEmpty();
  }
}