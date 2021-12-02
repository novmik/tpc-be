package com.novmik.tpc.cdt;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.SubjectOfRF;
import com.novmik.tpc.subject.SubjectOfRFService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.novmik.tpc.cdt.CdtConstant.*;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;

@AllArgsConstructor
@Service
public class CoefficientDifficultyTreatingService {

    private final CoefficientDifficultyTreatingRepository cdtRepository;
    private final CaseCdtService caseCdtService;
    private final SubjectOfRFService subjectOfRFService;

    protected List<CoefficientDifficultyTreating> getCareFacilityCdtpListBySubjectId(final Long idSubject, final String careFacility) {
        SubjectOfRF subjectById = subjectOfRFService.getSubjectById(idSubject).orElseThrow();
        return cdtRepository.findAllByNameSubjectAndCareFacility(subjectById.getNameSubject(), careFacility);
    }

    protected CoefficientDifficultyTreating addNewCoefficientDifficultyTreating(final CoefficientDifficultyTreating cdt) {
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
        if (!cdt.getCareFacility().equalsIgnoreCase(ROUND_THE_CLOCK_CARE_FACILITY) && !cdt.getCareFacility().equalsIgnoreCase(DAY_CARE_FACILITY)) {
            throw new BadRequestException(CARE_FACILITY_NOT_CORRECT + cdt.getCareFacility() + ". " + CARE_FACILITY_MUST_BE + ROUND_THE_CLOCK_CARE_FACILITY + " или " + DAY_CARE_FACILITY);
        }
        if (subjectOfRFService.findByNameSubject(cdt.getNameSubject()).isEmpty()) {
            throw new NotFoundException(SUBJECT_NOT_EXISTS + cdt.getNameSubject());
        }
        CaseCdt caseCdt = caseCdtService.save(cdt.getCaseCdt().getNominationCaseCdt());
        if (cdtRepository.existByCaseCdtIdAndNameSubjectAndCareFacility(cdt.getCaseCdt().getId(), cdt.getNameSubject(), cdt.getCareFacility())) {
            throw new BadRequestException(CDT_EXISTS + caseCdt);
        }
        cdt.setCaseCdt(caseCdt);
        return cdtRepository.save(cdt);
    }

}
