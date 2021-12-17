package com.novmik.tpc.cdt;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.SubjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * КСЛП business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings({"PMD.CommentSize", "PMD.LawOfDemeter"})
public class CoefficientDifficultyTreatingService {

  /**
   * ROUND_THE_CLOCK_CARE_FACILITY.
   * Круглосуточные стационар (КС)
   */
  public static final String ROUND_THE_CLOCK_CARE_FACILITY =
      "st";
  /**
   * DAY_CARE_FACILITY.
   * Дневной стационар (ДС)
   */
  public static final String DAY_CARE_FACILITY =
      "ds";

  /**
   * КСЛПRepository {@link CoefficientDifficultyTreatingRepository}.
   */
  private final CoefficientDifficultyTreatingRepository cdtRepository;
  /**
   * CaseCdtService {@link CaseCdtService}.
   */
  private final CaseCdtService caseCdtService;
  /**
   * SubjectService {@link SubjectService}.
   */
  private final SubjectService subjectService;

  /**
   * Список КСЛП по id субъекта РФ
   * и стационару.
   *
   * @param idSubject    id субъекта РФ
   * @param careFacility стационар
   * @return список КСЛП {@link CoefficientDifficultyTreating}
   */
  protected List<CoefficientDifficultyTreating> getCareFacilityCdtListBySubjectId(
      final Long idSubject, final String careFacility) {
    final String nameSubject = subjectService
        .getSubjectById(idSubject)
        .getNameSubject();
    return cdtRepository.findAllByNameSubjectAndCareFacility(nameSubject, careFacility);
  }

  /**
   * Добавление КСЛП.
   *
   * @param cdt КСЛП {@link CoefficientDifficultyTreating}
   * @return КСЛП {@link CoefficientDifficultyTreating}
   */
  protected CoefficientDifficultyTreating addNewCoefficientDifficultyTreating(
      final CoefficientDifficultyTreating cdt) {
    cdtValidationCheck(cdt);
    if (subjectService.findByNameSubject(cdt.getNameSubject()).isEmpty()) {
      throw new NotFoundException(
          "Субъекта с таким id/именем/названием не существует: " + cdt.getNameSubject());
    }
    final CaseCdt caseCdt = caseCdtService.save(cdt.getCaseCdt().getNominationCaseCdt());
    if (cdtRepository.existByCaseCdtIdAndNameSubjectAndCareFacility(cdt.getCaseCdt().getIdCaseCdt(),
        cdt.getNameSubject(), cdt.getCareFacility())) {
      throw new BadRequestException(
          "КСЛП с таким id/именем/названием уже существует: " + caseCdt);
    }
    cdt.setCaseCdt(caseCdt);
    return cdtRepository.save(cdt);
  }

  /**
   * Проверка КСЛП.
   *
   * @param cdt КСЛП {@link CoefficientDifficultyTreating}
   * @throws BadRequestException если некорректные
   *                             данные или КСЛП есть
   * @throws NotFoundException   если субъекта не найдено
   */
  private void cdtValidationCheck(final CoefficientDifficultyTreating cdt) {
    if (ObjectUtils.anyNull(
        cdt,
        cdt.getNameSubject(),
        cdt.getCaseCdt(),
        cdt.getValue(),
        cdt.getCareFacility()
    )) {
      throw new BadRequestException("Некорректные данные о КСЛП." + cdt);
    }
    if (cdt.getValue() <= 0) {
      throw new BadRequestException("Некорректное значение КСЛП: " + cdt.getValue());
    }
    if (!ROUND_THE_CLOCK_CARE_FACILITY.equalsIgnoreCase(cdt.getCareFacility())
        && !DAY_CARE_FACILITY.equalsIgnoreCase(cdt.getCareFacility())) {
      throw new BadRequestException(String.format(
              "Некорректное значение стационара: %s. Должно быть: %s или %s",
              cdt.getCareFacility(),
          ROUND_THE_CLOCK_CARE_FACILITY,
          DAY_CARE_FACILITY));
    }
  }

}
