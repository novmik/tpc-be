package com.novmik.tpc.medicament;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.novmik.tpc.medicament.MedicamentConstant.MEDICAMENT_LIST_ERROR;

@AllArgsConstructor
@Service
public class MedicamentService {

    private final SchemePharmacotherapyService schemeService;
    private final MedicamentExtractorResolver medicamentExtractorResolver;

    public List<Medicament> getMedicamentListBySchemePharmacotherapy(String codeScheme) {
        SchemePharmacotherapy byCodeScheme = schemeService.findByCodeScheme(codeScheme).orElseThrow();
        return getMedicamentListBySchemePharmacotherapy(byCodeScheme);
    }
    private List<Medicament> getMedicamentListBySchemePharmacotherapy(SchemePharmacotherapy schemePharmacotherapy) {
        List<Medicament> medicamentList = medicamentExtractorResolver.getMedicamentList(schemePharmacotherapy);
        if (medicamentList.isEmpty()) {
            throw new NotFoundException(MEDICAMENT_LIST_ERROR + schemePharmacotherapy);
        }
        return medicamentList;
    }

}
