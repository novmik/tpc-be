package com.novmik.tpc.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectOfRFRepository extends JpaRepository<SubjectOfRF, Long> {

    @Query(value = "select id, name_subject from subject_of_rf", nativeQuery = true)
    List<NameSubjectAndId> getIdAndNameSubjectFromTable();

    Optional<SubjectOfRF> findByNameSubject(String nameSubject);
}
