package com.novmik.tpc.medicament;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link MedicamentPrice}
 * data persistence layer operation interface..
 */
public interface MedicamentPriceRepository extends JpaRepository<MedicamentPrice, Long> {

  /**
   * Список {@link MedicamentPrice}.
   *
   * @param inn МНН
   * @return список {@link MedicamentPrice}
   */
  List<MedicamentPrice> findByInn(String inn);
}
