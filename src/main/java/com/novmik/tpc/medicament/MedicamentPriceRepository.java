package com.novmik.tpc.medicament;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicamentPriceRepository extends JpaRepository<MedicamentPrice, Long> {

    List<MedicamentPrice> findByInn(String inn);
}
