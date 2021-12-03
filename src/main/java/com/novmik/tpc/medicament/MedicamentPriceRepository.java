package com.novmik.tpc.medicament;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentPriceRepository extends JpaRepository<MedicamentPrice, Long> {

  List<MedicamentPrice> findByInn(String inn);
}
