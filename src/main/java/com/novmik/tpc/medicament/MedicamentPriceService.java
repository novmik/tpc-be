package com.novmik.tpc.medicament;

import com.novmik.tpc.exception.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * {@link MedicamentPrice} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class MedicamentPriceService {

  /**
   * {@link MedicamentPriceRepository}.
   */
  private final MedicamentPriceRepository mpRepository;

  /**
   * Список {@link MedicamentPrice} по МНН.
   *
   * @param inn МНН
   * @return список {@link MedicamentPrice}
   * @throws NotFoundException если {@link MedicamentPrice} не найден
   */
  public List<MedicamentPrice> getMedicalPriceList(final String inn) {
    final List<MedicamentPrice> medicamentPrices = mpRepository.findByInn(inn);
    if (medicamentPrices.isEmpty()) {
      throw new NotFoundException("Список лекарств не найден, МНН: " + inn);
    }
    return medicamentPrices;
  }

}
