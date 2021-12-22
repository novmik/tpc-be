package com.novmik.tpc.medicalinstitution;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * {@link MedicalInstitution} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentSize"})
public class MedicalInstitutionService {

  /**
   * {@link MedicalInstitutionRepository}.
   */
  private final MedicalInstitutionRepository miRepository;
  /**
   * {@link SubjectService}.
   */
  private final SubjectService subjectService;

  /**
   * Список id и наименование {@link MedicalInstitution}.
   * Возвращет проекцию {@link NameMedicalInstitutionAndId}
   *
   * @param idSubject id {@link Subject}
   * @return список {@link NameMedicalInstitutionAndId}
   * @throws NotFoundException если {@link Subject} не найден
   */
  protected List<NameMedicalInstitutionAndId> getMedicalInstitutionList(
      final Long idSubject) {
    final List<NameMedicalInstitutionAndId> allMiBySubjectId =
        miRepository.listIdAndMedicalInstitutionNameBySubjectId(idSubject);
    if (allMiBySubjectId.isEmpty()) {
      throw new NotFoundException(
          "Нет медицинских организаций с таким id Субъекта: " + idSubject);
    }
    return allMiBySubjectId;
  }

  /**
   * Поиск {@link MedicalInstitution} по id.
   *
   * @param idMi id {@link MedicalInstitution}
   * @return {@link MedicalInstitution}
   * @throws NotFoundException если {@link MedicalInstitution} не найден
   */
  public MedicalInstitution getMedicalInstitutionById(final Long idMi) {
    return miRepository.findById(idMi)
        .orElseThrow(() -> new NotFoundException(
            "Нет медицинских организаций с таким id: " + idMi));
  }

  /**
   * Список {@link MedicalInstitution},
   * сортированные по id.
   *
   * @param idSubject id {@link Subject}
   * @return список {@link MedicalInstitution}
   */
  public List<MedicalInstitution> getAllMedicalInstitutionsBySubjectId(
      final Long idSubject) {
    final String nameSubject = subjectService.getSubjectById(idSubject).getNameSubject();
    final List<MedicalInstitution> allMiBySubjectId = miRepository
        .findMedicalInstitutionsByNameSubject(nameSubject);
    return allMiBySubjectId
        .stream()
        .sorted(Comparator.comparing(MedicalInstitution::getIdMi))
        .collect(Collectors.toList());
  }

  /**
   * Добавление {@link MedicalInstitution}.
   *
   * @param medInstitution {@link MedicalInstitution}
   * @return {@link MedicalInstitution}
   * @throws BadRequestException если если некорректные данные
   * @throws BadRequestException если {@link MedicalInstitution} есть
   * @throws NotFoundException   если {@link Subject} не найден
   */
  protected MedicalInstitution addNewMedicalInstitution(
      final MedicalInstitution medInstitution) {
    if (ObjectUtils.anyNull(
        medInstitution,
        medInstitution.getNameMi(),
        medInstitution.getNameSubject()
    )) {
      throw new BadRequestException(
          "Некорректные данные о медицинской организации." + medInstitution);
    }
    if (miRepository.findByNameMiAndNameSubject(
            medInstitution.getNameMi(),
            medInstitution.getNameSubject()
        )
        .isPresent()) {
      throw new BadRequestException(
          "Медицинская организация с таким id/именем/названием уже существует: "
              + medInstitution.getNameMi());
    }
    if (subjectService.findByNameSubject(medInstitution.getNameSubject()).isEmpty()) {
      throw new NotFoundException(
          "Субъекта с таким id/именем/названием не существует: "
              + medInstitution.getNameSubject());
    }
    return miRepository.save(medInstitution);
  }

  /**
   * Изменение {@link MedicalInstitution}.
   *
   * @param medInstitution {@link MedicalInstitution}
   * @return {@link MedicalInstitution}
   * @throws BadRequestException если некорректные данные
   * @throws BadRequestException если {@link MedicalInstitution} не найден
   */
  protected MedicalInstitution updateMedicalInstitution(
      final MedicalInstitution medInstitution) {
    if (ObjectUtils.anyNull(
        medInstitution,
        medInstitution.getIdMi(),
        medInstitution.getNameMi(),
        medInstitution.getNameSubject()
    )) {
      throw new BadRequestException(
          "Некорректные данные о медицинской организации." + medInstitution);
    }
    if (!miRepository.existsById(medInstitution.getIdMi())) {
      throw new BadRequestException(
          "Нет медицинских организаций с таким id: " + medInstitution.getIdMi());
    }
    return miRepository.save(medInstitution);
  }

  /**
   * Удаление {@link MedicalInstitution}.
   *
   * @param idMi id {@link MedicalInstitution}
   * @throws BadRequestException если id не корректный
   * @throws BadRequestException если {@link MedicalInstitution} не найден
   */
  protected void deleteMedicalInstitutionById(
      final Long idMi) {
    if (idMi == null || idMi < 1) {
      throw new BadRequestException("Некорректные данные о медицинской организации.");
    }
    if (!miRepository.existsById(idMi)) {
      throw new BadRequestException(
          "Нет медицинских организаций с таким id: " + idMi);
    }
    miRepository.deleteById(idMi);
  }
}
