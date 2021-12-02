package com.novmik.tpc.subject;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_EXISTS;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_CORRECT;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;

@AllArgsConstructor
@Service
public class SubjectOfRFService {

    private final SubjectOfRFRepository subjectOfRFRepository;

    public Optional<SubjectOfRF> findByNameSubject(final String nameSubject) {
        return subjectOfRFRepository.findByNameSubject(nameSubject);
    }

    protected SubjectOfRF save(final SubjectOfRF subjectOfRF) {
        return subjectOfRFRepository.save(subjectOfRF);
    }

    public boolean existsById(final Long idSubject) {
        return subjectOfRFRepository.existsById(idSubject);
    }

    protected List<NameSubjectAndId> getIdAndNameSubjectFromTable() {
        return subjectOfRFRepository.getIdAndNameSubjectFromTable();
    }

    public List<SubjectOfRF> getAllSubject() {
        return subjectOfRFRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(SubjectOfRF::getId))
                .collect(Collectors.toList());
    }

    public Optional<SubjectOfRF> getSubjectById(final Long idSubject) {
        Optional<SubjectOfRF> subjectOfRFRepositoryById = subjectOfRFRepository
                .findById(idSubject);
        if (subjectOfRFRepositoryById.isEmpty()) {
            throw new NotFoundException(SUBJECT_NOT_EXISTS + idSubject);
        }
        return subjectOfRFRepositoryById;
    }

    protected SubjectOfRF addNewSubject(final SubjectOfRF subjectOfRF) {
        if (ObjectUtils.anyNull(
                subjectOfRF,
                subjectOfRF.getNameSubject(),
                subjectOfRF.getBaseRateDayCareFacility(),
                subjectOfRF.getBaseRateRoundTheClockCareFacility()
        )) {
            throw new BadRequestException(SUBJECT_NOT_CORRECT + subjectOfRF);
        }
        if (findByNameSubject(
                subjectOfRF.getNameSubject()).isPresent()) {
            throw new BadRequestException(
                    SUBJECT_EXISTS + subjectOfRF.getNameSubject());
        }
        return save(subjectOfRF);
    }

    protected SubjectOfRF updateSubject(final SubjectOfRF subjectOfRF) {
        if (ObjectUtils.anyNull(
                subjectOfRF,
                subjectOfRF.getId(),
                subjectOfRF.getNameSubject(),
                subjectOfRF.getBaseRateDayCareFacility(),
                subjectOfRF.getBaseRateRoundTheClockCareFacility()
        )) {
            throw new BadRequestException(SUBJECT_NOT_CORRECT + subjectOfRF);
        }
        if (!existsById(subjectOfRF.getId())) {
            throw new NotFoundException(
                    SUBJECT_NOT_EXISTS + subjectOfRF.getId());
        }
        return save(subjectOfRF);
    }

    protected void deleteSubjectById(final Long idSubject) {
        if (idSubject == null || idSubject < 1) {
            throw new BadRequestException(SUBJECT_NOT_CORRECT);
        }
        if (!existsById(idSubject)) {
            throw new NotFoundException(SUBJECT_NOT_EXISTS + idSubject);
        }
        subjectOfRFRepository.deleteById(idSubject);
    }
}
