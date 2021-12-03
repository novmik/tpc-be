package com.novmik.tpc.cdt;

import static com.novmik.tpc.cdt.CdtConstant.CARE_FACILITY_MUST_BE;
import static com.novmik.tpc.cdt.CdtConstant.CARE_FACILITY_NOT_CORRECT;
import static com.novmik.tpc.cdt.CdtConstant.CDT_EXISTS;
import static com.novmik.tpc.cdt.CdtConstant.CDT_NOT_CORRECT;
import static com.novmik.tpc.cdt.CdtConstant.CDT_VALUE_NOT_CORRECT;
import static com.novmik.tpc.cdt.CdtConstant.DAY_CARE_FACILITY;
import static com.novmik.tpc.cdt.CdtConstant.ROUND_THE_CLOCK_CARE_FACILITY;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CoefficientDifficultyTreatingService {

  private final CoefficientDifficultyTreatingRepository cdtRepository;
  private final CaseCdtService caseCdtService;
  private final SubjectService subjectService;

  protected List<CoefficientDifficultyTreating> getCareFacilityCdtpListBySubjectId(
      final Long idSubject, final String careFacility) {
    Subject subjectById = subjectService.getSubjectById(idSubject).orElseThrow();
    return cdtRepository.findAllByNameSubjectAndCareFacility(subjectById.getNameSubject(),
        careFacility);
  }

  protected CoefficientDifficultyTreating addNewCoefficientDifficultyTreating(
      final CoefficientDifficultyTreating cdt) {
    if (ObjectUtils.anyNull(
        cdt,
        cdt.getNameSubject(),
        cdt.getCaseCdt(),
        cdt.getValueNominationCaseCdt(),
        cdt.getCareFacility()
    )) {
      throw new BadRequestException(CDT_NOT_CORRECT + cdt);
    }
    if (cdt.getValueNominationCaseCdt() <= 0) {
      throw new BadRequestException(CDT_VALUE_NOT_CORRECT + cdt.getValueNominationCaseCdt());
    }
    if (!ROUND_THE_CLOCK_CARE_FACILITY.equalsIgnoreCase(cdt.getCareFacility())
        && !DAY_CARE_FACILITY.equalsIgnoreCase(cdt.getCareFacility())) {
      throw new BadRequestException(
          CARE_FACILITY_NOT_CORRECT + cdt.getCareFacility() + ". " + CARE_FACILITY_MUST_BE
              + ROUND_THE_CLOCK_CARE_FACILITY + " или " + DAY_CARE_FACILITY);
    }
    if (subjectService.findByNameSubject(cdt.getNameSubject()).isEmpty()) {
      throw new NotFoundException(SUBJECT_NOT_EXISTS + cdt.getNameSubject());
    }
    CaseCdt caseCdt = caseCdtService.save(cdt.getCaseCdt().getNominationCaseCdt());
    if (cdtRepository.existByCaseCdtIdAndNameSubjectAndCareFacility(cdt.getCaseCdt().getId(),
        cdt.getNameSubject(), cdt.getCareFacility())) {
      throw new BadRequestException(CDT_EXISTS + caseCdt);
    }
    cdt.setCaseCdt(caseCdt);
    return cdtRepository.save(cdt);
  }

}
