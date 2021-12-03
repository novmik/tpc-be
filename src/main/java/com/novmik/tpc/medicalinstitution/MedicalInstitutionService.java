package com.novmik.tpc.medicalinstitution;

import static com.novmik.tpc.medicalinstitution.MedicalInstitutionConstant.MEDICAL_INSTITUTIONS_NOT_EXISTS;
import static com.novmik.tpc.medicalinstitution.MedicalInstitutionConstant.MEDICAL_INSTITUTIONS_NOT_EXISTS_BY_ID_SUBJECT;
import static com.novmik.tpc.medicalinstitution.MedicalInstitutionConstant.MEDICAL_INSTITUTION_EXISTS;
import static com.novmik.tpc.medicalinstitution.MedicalInstitutionConstant.MEDICAL_INSTITUTION_NOT_CORRECT;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.SubjectService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class MedicalInstitutionService {

  private final MedicalInstitutionRepository medicalInstitutionRepository;
  private final SubjectService subjectService;

  protected List<NameMedicalInstitutionAndId> getMedicalInstitutionList(
      final Long idSubject) {
    List<NameMedicalInstitutionAndId> allMedicalInstitutionBySubjectId =
        medicalInstitutionRepository.listIdAndMedicalInstitutionNameBySubjectId(idSubject);
    if (allMedicalInstitutionBySubjectId.isEmpty()) {
      throw new NotFoundException(MEDICAL_INSTITUTIONS_NOT_EXISTS_BY_ID_SUBJECT + idSubject);
    }
    return allMedicalInstitutionBySubjectId;
  }

  public Optional<MedicalInstitution> getMedicalInstitutionById(
      final Long idMedicalInstitution) {
    Optional<MedicalInstitution> medicalInstitutionRepositoryById =
        medicalInstitutionRepository.findById(idMedicalInstitution);
    if (medicalInstitutionRepositoryById.isEmpty()) {
      throw new NotFoundException(MEDICAL_INSTITUTIONS_NOT_EXISTS + idMedicalInstitution);
    }
    return medicalInstitutionRepositoryById;
  }

  public List<MedicalInstitution> getAllMedicalInstitutionsBySubjectId(
      final Long idSubject) {
    String nameSubject = subjectService.getSubjectById(idSubject).orElseThrow().getNameSubject();
    List<MedicalInstitution> allMedicalInstitutionBySubjectId = medicalInstitutionRepository
        .findMedicalInstitutionsByNameSubject(nameSubject);
    return allMedicalInstitutionBySubjectId.stream()
        .sorted(Comparator.comparing(MedicalInstitution::getId)).collect(Collectors.toList());
  }

  protected MedicalInstitution addNewMedicalInstitution(
      final MedicalInstitution medicalInstitution) {
    if (ObjectUtils.anyNull(
        medicalInstitution,
        medicalInstitution.getNameMedicalInstitution(),
        medicalInstitution.getNameSubject()
    )) {
      throw new BadRequestException(MEDICAL_INSTITUTION_NOT_CORRECT + medicalInstitution);
    }
    if (medicalInstitutionRepository.findByNameMedicalInstitutionAndNameSubject(
            medicalInstitution.getNameMedicalInstitution(),
            medicalInstitution.getNameSubject()
        )
        .isPresent()) {
      throw new BadRequestException(
          MEDICAL_INSTITUTION_EXISTS + medicalInstitution.getNameMedicalInstitution());
    }
    if (subjectService.findByNameSubject(medicalInstitution.getNameSubject()).isEmpty()) {
      throw new NotFoundException(SUBJECT_NOT_EXISTS + medicalInstitution.getNameSubject());
    }
    return medicalInstitutionRepository.save(medicalInstitution);
  }

  protected MedicalInstitution updateMedicalInstitution(
      final MedicalInstitution medicalInstitution) {
    if (ObjectUtils.anyNull(
        medicalInstitution,
        medicalInstitution.getId(),
        medicalInstitution.getNameMedicalInstitution(),
        medicalInstitution.getNameSubject()
    )) {
      throw new BadRequestException(MEDICAL_INSTITUTION_NOT_CORRECT + medicalInstitution);
    }
    if (!medicalInstitutionRepository.existsById(medicalInstitution.getId())) {
      throw new BadRequestException(MEDICAL_INSTITUTIONS_NOT_EXISTS + medicalInstitution.getId());
    }
    return medicalInstitutionRepository.save(medicalInstitution);
  }

  protected void deleteMedicalInstitutionById(
      final Long idMedicalInstitution) {
    if (idMedicalInstitution == null || idMedicalInstitution < 1) {
      throw new BadRequestException(MEDICAL_INSTITUTION_NOT_CORRECT);
    }
    if (!medicalInstitutionRepository.existsById(idMedicalInstitution)) {
      throw new BadRequestException(MEDICAL_INSTITUTIONS_NOT_EXISTS + idMedicalInstitution);
    }
    medicalInstitutionRepository.deleteById(idMedicalInstitution);
  }
}
