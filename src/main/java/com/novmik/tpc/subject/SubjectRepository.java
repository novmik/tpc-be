package com.novmik.tpc.subject;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

  @Query(value = """
      select id, name_subject from subject_of_rf
      """, nativeQuery = true)
  List<NameSubjectAndId> getIdAndNameSubjectFromTable();

  Optional<Subject> findByNameSubject(String nameSubject);
}
