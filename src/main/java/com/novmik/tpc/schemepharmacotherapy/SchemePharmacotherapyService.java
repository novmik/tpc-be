package com.novmik.tpc.schemepharmacotherapy;

import static com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyConstants.SCHEME_PHARMACOTHERAPY_NOT_EXISTS;

import com.novmik.tpc.exception.NotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Схема лекарственной терапии business interface layer.
 */
@SuppressWarnings("PMD.LawOfDemeter")
@AllArgsConstructor
@Service
public class SchemePharmacotherapyService {

  /**
   * SchemePharmacotherapyRepository {@link SchemePharmacotherapyRepository}.
   */
  private final SchemePharmacotherapyRepository spRepository;

  /**
   * Поиск СЛТ по коду схемы.
   * (Схема лекарственной терапии)
   *
   * @param codeScheme код СЛТ
   * @return СЛТ {@link SchemePharmacotherapy}
   * @throws NotFoundException если СЛТ не найдено
   */
  public Optional<SchemePharmacotherapy> findByCodeScheme(final String codeScheme) {
    final Optional<SchemePharmacotherapy> byCodeScheme = spRepository.findByCodeScheme(
        codeScheme);
    if (byCodeScheme.isEmpty()) {
      throw new NotFoundException(SCHEME_PHARMACOTHERAPY_NOT_EXISTS + codeScheme);
    }
    return byCodeScheme;
  }
}
