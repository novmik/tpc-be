package com.novmik.tpc.subject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class SubjectOfRFRepositoryTest {

    @Autowired
    private SubjectOfRFRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canGetIdAndNameSubjectListFromTable() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                "Test SubjectName",
                1.00D,
                1.00D);
        subjectOfRF = underTest.save(subjectOfRF);
        List<NameSubjectAndId> idAndNameSubjectFromTable = underTest.getIdAndNameSubjectFromTable();
        assertThat(subjectOfRF.getId()).isNotNull();
        assertThat(idAndNameSubjectFromTable.size()).isEqualTo(1);
    }

    @Test
    void canFindByNameSubject() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                "Test SubjectName",
                1.00D,
                1.00D);
        subjectOfRF = underTest.save(subjectOfRF);
        SubjectOfRF actualSubject = underTest.findByNameSubject(subjectOfRF.getNameSubject()).orElse(null);
        assertThat(subjectOfRF).hasNoNullFieldsOrProperties();
        assertThat(subjectOfRF).isEqualTo(actualSubject);
    }

    @Test
    void notFindByNameSubject() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                "Test SubjectName",
                1.00D,
                1.00D);
        subjectOfRF = underTest.save(subjectOfRF);
        Optional<SubjectOfRF> byNameSubject = underTest.findByNameSubject("test");
        assertThat(byNameSubject).isEmpty();
    }
}