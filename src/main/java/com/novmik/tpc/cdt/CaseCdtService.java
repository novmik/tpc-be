package com.novmik.tpc.cdt;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Случай КСЛП business interface layer.
 */
@AllArgsConstructor
@Service
public class CaseCdtService {

  /**
   * CaseCdtRepository {@link CaseCdtRepository}.
   */
  private final CaseCdtRepository caseCdtRepository;

  /**
   * Поиск случая КСЛП по наименованию.
   *
   * @param nominationCaseCdt наименование случая КСЛП
   * @return случай КСЛП {@link CaseCdt}
   */
  protected Optional<CaseCdt> findByNominationCaseCdt(final String nominationCaseCdt) {
    return caseCdtRepository.findByNominationCaseCdt(nominationCaseCdt);
  }

  /**
   * Сохранение случая КСЛП.
   *
   * @param caseCdt случай КСЛП {@link CaseCdt}
   * @return случай КСЛП {@link CaseCdt}
   */
  protected CaseCdt save(final CaseCdt caseCdt) {
    return caseCdtRepository.save(caseCdt);
  }

  /**
   * Сохранение случая КСЛП.
   *
   * @param nominationCaseCdt наименование случая КСЛП
   * @return случай КСЛП {@link CaseCdt}
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  protected CaseCdt save(final String nominationCaseCdt) {
    final Optional<CaseCdt> optionalCaseCdt = findByNominationCaseCdt(nominationCaseCdt);
    return optionalCaseCdt.orElseGet(() -> save(new CaseCdt(nominationCaseCdt)));
  }
}
