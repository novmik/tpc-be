package com.novmik.tpc.subject;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * {@link Subject} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class SubjectService {

  /**
   * SubjectRepository {@link SubjectRepository}.
   */
  private final SubjectRepository subjectRepository;

  /**
   * Поиск {@link Subject} по наименование.
   *
   * @param nameSubject наименование {@link Subject}
   * @return {@link Subject}
   */
  public Optional<Subject> findByNameSubject(final String nameSubject) {
    return subjectRepository.findByNameSubject(nameSubject);
  }

  /**
   * Сохранение {@link Subject}.
   *
   * @param subject {@link Subject}
   * @return {@link Subject}
   */
  protected Subject save(final Subject subject) {
    return subjectRepository.save(subject);
  }

  /**
   * Наличие {@link Subject}.
   *
   * @param idSubject id {@link Subject}
   * @return наличие
   */
  public boolean existsById(final Long idSubject) {
    return subjectRepository.existsById(idSubject);
  }

  /**
   * Список id и наименование {@link Subject}.
   * Возвращает проекцию {@link NameSubjectAndId}
   *
   * @return список {@link NameSubjectAndId}
   */
  protected List<NameSubjectAndId> getIdAndNameSubjectFromTable() {
    return subjectRepository.getIdAndNameSubjectFromTable();
  }

  /**
   * Список {@link Subject},
   * сортированный по id.
   *
   * @return список {@link Subject}
   */
  public List<Subject> getAllSubject() {
    return subjectRepository
        .findAll()
        .stream()
        .sorted(Comparator.comparing(Subject::getIdSubject))
        .collect(Collectors.toList());
  }

  /**
   * Поиск {@link Subject} по id.
   *
   * @param idSubject id {@link Subject}
   * @return {@link Subject}
   * @throws NotFoundException если {@link Subject} не найден
   */
  public Subject getSubjectById(final Long idSubject) {
    return subjectRepository.findById(idSubject)
        .orElseThrow(() -> new NotFoundException(
            "Субъекта с таким id/именем/названием не существует: " + idSubject));
  }

  /**
   * Добавление {@link Subject}.
   *
   * @param subject {@link Subject} без id
   * @return {@link Subject}
   * @throws BadRequestException если некорректные данные
   *                             если {@link Subject} есть
   */
  protected Subject addNewSubject(final Subject subject) {
    if (ObjectUtils.anyNull(
        subject,
        subject.getNameSubject(),
        subject.getBaseRateDs(),
        subject.getBaseRateSt()
    )) {
      throw new BadRequestException("Некорректные данные о субъекте." + subject);
    }
    if (findByNameSubject(subject.getNameSubject()).isPresent()) {
      throw new BadRequestException(
          "Субъект с таким id/именем/названием уже существует: " + subject.getNameSubject());
    }
    return save(subject);
  }

  /**
   * Обновление {@link Subject}.
   *
   * @param subject {@link Subject}
   * @return {@link Subject}
   * @throws BadRequestException если некорректные данные
   * @throws NotFoundException   если {@link Subject} не найден
   */
  protected Subject updateSubject(final Subject subject) {
    if (ObjectUtils.anyNull(
        subject,
        subject.getIdSubject(),
        subject.getNameSubject(),
        subject.getBaseRateDs(),
        subject.getBaseRateSt()
    )) {
      throw new BadRequestException("Некорректные данные о субъекте." + subject);
    }
    if (!existsById(subject.getIdSubject())) {
      throw new NotFoundException(
          "Субъекта с таким id/именем/названием не существует: " + subject.getIdSubject());
    }
    return save(subject);
  }

  /**
   * Удаление {@link Subject}.
   *
   * @param idSubject id {@link Subject}
   * @throws BadRequestException если id не корректный
   * @throws NotFoundException   если {@link Subject} не найден
   */
  protected void deleteSubjectById(final Long idSubject) {
    if (idSubject == null || idSubject < 1) {
      throw new BadRequestException("Некорректные данные о субъекте.");
    }
    if (!existsById(idSubject)) {
      throw new NotFoundException(
          "Субъекта с таким id/именем/названием не существует: " + idSubject);
    }
    subjectRepository.deleteById(idSubject);
  }
}
