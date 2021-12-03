package com.novmik.tpc.medicament;

import static com.novmik.tpc.medicament.MedicamentConstant.MEDICAMENT_PRICE_LIST_NOT_FOUND;

import com.novmik.tpc.exception.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MedicamentPriceService {

  private final MedicamentPriceRepository medicamentPriceRepository;

  public List<MedicamentPrice> getMedicalPriceList(final String inn) {
    List<MedicamentPrice> medicamentPricesByInn = medicamentPriceRepository.findByInn(inn);
    if (medicamentPricesByInn.isEmpty()) {
      throw new NotFoundException(MEDICAMENT_PRICE_LIST_NOT_FOUND + inn);
    }
    return medicamentPricesByInn;
  }

}
