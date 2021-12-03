package com.novmik.tpc.cdt;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CaseCdtService {

  private final CaseCdtRepository caseCdtRepository;

  protected Optional<CaseCdt> findByNominationCaseCdt(final String nominationCaseCdt) {
    return caseCdtRepository.findByNominationCaseCdt(nominationCaseCdt);
  }

  protected CaseCdt save(final CaseCdt caseCdt) {
    return caseCdtRepository.save(caseCdt);
  }

  protected CaseCdt save(final String nominationCaseCdt) {
    Optional<CaseCdt> byNominationCaseCdt = findByNominationCaseCdt(nominationCaseCdt);
    return byNominationCaseCdt.orElseGet(() -> save(new CaseCdt(nominationCaseCdt)));
  }
}
