package com.novmik.tpc.medicament;

import com.novmik.tpc.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.novmik.tpc.medicament.MedicamentConstant.MEDICAMENT_PRICE_LIST_NOT_FOUND;

@AllArgsConstructor
@Service
public class MedicamentPriceService {

    private final MedicamentPriceRepository medicamentPriceRepository;

    public List<MedicamentPrice> getMedicalPriceList(String inn) {
        List<MedicamentPrice> medicamentPricesByInn = medicamentPriceRepository.findByInn(inn);
        if (medicamentPricesByInn.isEmpty()) {
            throw new NotFoundException(MEDICAMENT_PRICE_LIST_NOT_FOUND + inn);
        }
        return medicamentPricesByInn;
    }

}
