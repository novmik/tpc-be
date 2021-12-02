package com.novmik.tpc.medicament;

import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.novmik.tpc.medicament.MedicamentConstant.MEDICAMENT_LIST_ERROR;

@AllArgsConstructor
@Service
public class MedicamentService {

    private final SchemePharmacotherapyService schemeService;

    public List<Medicament> getMedicamentListBySchemePharmacotherapy(final String codeScheme) {
        SchemePharmacotherapy byCodeScheme = schemeService.findByCodeScheme(codeScheme).orElseThrow();
        return getMedicamentListBySchemePharmacotherapy(byCodeScheme);
    }
    private List<Medicament> getMedicamentListBySchemePharmacotherapy(final SchemePharmacotherapy schemePharmacotherapy) {
        List<Medicament> medicamentList = MedicamentExtractorResolver.getMedicamentList(schemePharmacotherapy);
        if (medicamentList.isEmpty()) {
            throw new NotFoundException(MEDICAMENT_LIST_ERROR + schemePharmacotherapy);
        }
        return medicamentList;
    }

}
