package com.novmik.tpc.diagnosis;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisPriceRepository extends JpaRepository<DiagnosisPrice, Long> {

  List<DiagnosisPrice> findAllByNameSubject(String nameSubject);
}
