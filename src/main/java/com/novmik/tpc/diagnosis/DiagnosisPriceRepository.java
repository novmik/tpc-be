package com.novmik.tpc.diagnosis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosisPriceRepository extends JpaRepository<DiagnosisPrice, Long> {

    List<DiagnosisPrice> findAllByNameSubject(String nameSubject);
}
