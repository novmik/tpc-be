package com.novmik.tpc.schemepharmacotherapy;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * Схема лекарственной терапии business interface layer.
 */
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentSize"})
@AllArgsConstructor
@Service
public class SchemePharmacotherapyService {

  /**
   * SchemePharmacotherapyRepository {@link SchemePharmacotherapyRepository}.
   */
  private final SchemePharmacotherapyRepository schemeRepository;

  /**
   * Список {@link SchemePharmacotherapy}.
   *
   * @return список {@link SchemePharmacotherapy}
   */
  protected List<SchemePharmacotherapy> getAllSchemes() {
    return schemeRepository.findAll();
  }

  /**
   * Поиск СЛТ по коду схемы.
   * (Схема лекарственной терапии)
   *
   * @param codeScheme код СЛТ
   * @return СЛТ {@link SchemePharmacotherapy}
   * @throws NotFoundException если СЛТ не найдено
   */
  public SchemePharmacotherapy findByCodeScheme(final String codeScheme) {
    return schemeRepository.findByCodeScheme(codeScheme)
        .orElseThrow(() -> new NotFoundException(
            "Схема лекарственной терапии не найдена: " + codeScheme));
  }

  /**
   * Обновление {@link SchemePharmacotherapy}.
   *
   * @param scheme {@link SchemePharmacotherapy}
   * @return {@link SchemePharmacotherapy}
   * @throws BadRequestException если некорректные данные
   * @throws NotFoundException   если {@link SchemePharmacotherapy} не найден
   */
  protected SchemePharmacotherapy updateScheme(final SchemePharmacotherapy scheme) {
    if (ObjectUtils.anyNull(
        scheme,
        scheme.getIdScheme(),
        scheme.getCodeScheme(),
        scheme.getInnMedicament(),
        scheme.getDescription(),
        scheme.getDaysTreatments()
    )) {
      throw new BadRequestException("Некорректные данные о СЛТ: " + scheme);
    }
    if (!schemeRepository.existsById(scheme.getIdScheme())) {
      throw new NotFoundException(
          "СЛТ с таким id/именем/названием не существует: " + scheme.getIdScheme());
    }
    return schemeRepository.save(scheme);
  }
}
