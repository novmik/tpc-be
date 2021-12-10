package com.novmik.tpc.cdt;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.SubjectConstants;
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
  protected List<CoefficientDifficultyTreating> getCareFacilityCdtpListBySubjectId(
      final Long idSubject, final String careFacility) {
    final String nameSubject = subjectService
        .getSubjectById(idSubject)
        .orElseThrow()
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
      throw new NotFoundException(SubjectConstants.SUBJECT_NOT_EXISTS + cdt.getNameSubject());
    }
    final CaseCdt caseCdt = caseCdtService.save(cdt.getCaseCdt().getNominationCaseCdt());
    if (cdtRepository.existByCaseCdtIdAndNameSubjectAndCareFacility(cdt.getCaseCdt().getIdCaseCdt(),
        cdt.getNameSubject(), cdt.getCareFacility())) {
      throw new BadRequestException(CdtConstants.CDT_EXISTS + caseCdt);
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
      throw new BadRequestException(CdtConstants.CDT_NOT_CORRECT + cdt);
    }
    if (cdt.getValue() <= 0) {
      throw new BadRequestException(CdtConstants.CDT_VALUE_NOT_CORRECT + cdt.getValue());
    }
    if (!CdtConstants.ROUND_THE_CLOCK_CARE_FACILITY.equalsIgnoreCase(cdt.getCareFacility())
        && !CdtConstants.DAY_CARE_FACILITY.equalsIgnoreCase(cdt.getCareFacility())) {
      throw new BadRequestException(
          CdtConstants.CARE_FACILITY_NOT_CORRECT + cdt.getCareFacility() + ". "
              + CdtConstants.CARE_FACILITY_MUST_BE
              + CdtConstants.ROUND_THE_CLOCK_CARE_FACILITY + " или "
              + CdtConstants.DAY_CARE_FACILITY);
    }
  }

}
