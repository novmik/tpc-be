package com.novmik.tpc.schemepharmacotherapy;

import com.novmik.tpc.exception.NotFoundException;
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
  public SchemePharmacotherapy findByCodeScheme(final String codeScheme) {
    return spRepository.findByCodeScheme(codeScheme)
        .orElseThrow(() -> new NotFoundException(
            "Схема лекарственной терапии не найдена: " + codeScheme));
  }
}
