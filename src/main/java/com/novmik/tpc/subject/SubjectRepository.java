package com.novmik.tpc.subject;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * {@link Subject}
 * data persistence layer operation interface.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

  /**
   * Список id и наименование {@link Subject}
   * Возвращает проекцию {@link NameSubjectAndId}.
   *
   * @return список {@link NameSubjectAndId}
   */
  @Query(value = """
      select id, name_subject from subject_of_rf
      """, nativeQuery = true)
  List<NameSubjectAndId> getIdAndNameSubjectFromTable();

  /**
   * Поиск {@link Subject} по наименованию.
   *
   * @param nameSubject наименование {@link Subject}
   * @return {@link Subject}
   */
  Optional<Subject> findByNameSubject(String nameSubject);
}
