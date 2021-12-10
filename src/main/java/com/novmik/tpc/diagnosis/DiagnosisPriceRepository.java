package com.novmik.tpc.diagnosis;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.novmik.tpc.subject.Subject;

/**
 * {@link DiagnosisPrice} data
 * persistence layer operation interface.
 */
public interface DiagnosisPriceRepository extends JpaRepository<DiagnosisPrice, Long> {

  /**
   * Список {@link DiagnosisPrice}.
   *
   * @param nameSubject наименование {@link Subject}
   * @return список {@link DiagnosisPrice}
   */
  List<DiagnosisPrice> findAllByNameSubject(String nameSubject);
}
