package com.novmik.tpc.subject;

import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_EXISTS;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_CORRECT;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;



@AllArgsConstructor
@Service
public class SubjectService {

  private final SubjectRepository subjectRepository;

  public Optional<Subject> findByNameSubject(final String nameSubject) {
    return subjectRepository.findByNameSubject(nameSubject);
  }

  protected Subject save(final Subject subject) {
    return subjectRepository.save(subject);
  }

  public boolean existsById(final Long idSubject) {
    return subjectRepository.existsById(idSubject);
  }

  protected List<NameSubjectAndId> getIdAndNameSubjectFromTable() {
    return subjectRepository.getIdAndNameSubjectFromTable();
  }

  public List<Subject> getAllSubject() {
    return subjectRepository.findAll().stream()
        .sorted(Comparator.comparing(Subject::getId))
        .collect(Collectors.toList());
  }

  public Optional<Subject> getSubjectById(final Long idSubject) {
    Optional<Subject> subjectRepositoryById = subjectRepository.findById(idSubject);
    if (subjectRepositoryById.isEmpty()) {
      throw new NotFoundException(SUBJECT_NOT_EXISTS + idSubject);
    }
    return subjectRepositoryById;
  }

  protected Subject addNewSubject(final Subject subject) {
    if (ObjectUtils.anyNull(
        subject,
        subject.getNameSubject(),
        subject.getBaseRateDayCareFacility(),
        subject.getBaseRateRoundTheClockCareFacility())) {
      throw new BadRequestException(SUBJECT_NOT_CORRECT + subject);
    }
    if (findByNameSubject(subject.getNameSubject()).isPresent()) {
      throw new BadRequestException(SUBJECT_EXISTS + subject.getNameSubject());
    }
    return save(subject);
  }

  protected Subject updateSubject(final Subject subject) {
    if (ObjectUtils.anyNull(
        subject,
        subject.getId(),
        subject.getNameSubject(),
        subject.getBaseRateDayCareFacility(),
        subject.getBaseRateRoundTheClockCareFacility())) {
      throw new BadRequestException(SUBJECT_NOT_CORRECT + subject);
    }
    if (!existsById(subject.getId())) {
      throw new NotFoundException(SUBJECT_NOT_EXISTS + subject.getId());
    }
    return save(subject);
  }

  protected void deleteSubjectById(final Long idSubject) {
    if (idSubject == null || idSubject < 1) {
      throw new BadRequestException(SUBJECT_NOT_CORRECT);
    }
    if (!existsById(idSubject)) {
      throw new NotFoundException(SUBJECT_NOT_EXISTS + idSubject);
    }
    subjectRepository.deleteById(idSubject);
  }
}
