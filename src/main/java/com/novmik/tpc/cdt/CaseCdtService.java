package com.novmik.tpc.cdt;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CaseCdtService {

    private final CaseCdtRepository caseCdtRepository;

    Optional<CaseCdt> findByNominationCaseCdt(String nominationCaseCdt) {
        return caseCdtRepository.findByNominationCaseCdt(nominationCaseCdt);
    }

    CaseCdt save(CaseCdt caseCdt) { return caseCdtRepository.save(caseCdt); }

    CaseCdt save(String nominationCaseCdt) {
        Optional<CaseCdt> byNominationCaseCdt = findByNominationCaseCdt(nominationCaseCdt);
        return byNominationCaseCdt.orElseGet(() -> save(new CaseCdt(nominationCaseCdt)));
    }
}
