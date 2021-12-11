package com.novmik.tpc.drg;

import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstants.DRG_EXISTS;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstants.DRG_NOT_CORRECT;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstants.DRG_NOT_EXISTS;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstants.DRG_NOT_EXISTS_BY_NAME_DRG;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * {@link DiagnosisRelatedGroups} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class DiagnosisRelatedGroupsService {

  /**
   * {@link DiagnosisRelatedGroupsRepository}.
   */
  private final DiagnosisRelatedGroupsRepository drgRepository;

  /**
   * Поиск {@link DiagnosisRelatedGroups}.
   *
   * @param drg номер КСГ
   * @return {@link DiagnosisRelatedGroups}
   */
  public Optional<DiagnosisRelatedGroups> byNumberDrg(final String drg) {
    final Optional<DiagnosisRelatedGroups> byNumberDrg = drgRepository.findByNumberDrg(drg);
    if (byNumberDrg.isEmpty()) {
      throw new NotFoundException(DRG_NOT_EXISTS_BY_NAME_DRG + drg);
    }
    return byNumberDrg;
  }

  /**
   * Добавление {@link DiagnosisRelatedGroups}.
   *
   * @param drg {@link DiagnosisRelatedGroups}
   * @return {@link DiagnosisRelatedGroups}
   * @throws BadRequestException если некорректные данные
   *                             если {@link DiagnosisRelatedGroups} есть
   */
  protected DiagnosisRelatedGroups addNewDrg(final DiagnosisRelatedGroups drg) {
    if (ObjectUtils.anyNull(
        drg,
        drg.getNumberDrg(),
        drg.getNominationDrg(),
        drg.getRateIntensity(),
        drg.getWageShare()
    )) {
      throw new BadRequestException(DRG_NOT_CORRECT + drg);
    }
    if (drgRepository.findByNumberDrg(drg.getNumberDrg()).isPresent()) {
      throw new BadRequestException(DRG_EXISTS + drg.getNumberDrg());
    }
    return drgRepository.save(drg);
  }

  /**
   * Изменение {@link DiagnosisRelatedGroups}.
   *
   * @param drg {@link DiagnosisRelatedGroups}
   * @return {@link DiagnosisRelatedGroups}
   */
  protected DiagnosisRelatedGroups updateDrg(final DiagnosisRelatedGroups drg) {
    if (ObjectUtils.anyNull(
        drg,
        drg.getIdDrg(),
        drg.getNumberDrg(),
        drg.getNominationDrg(),
        drg.getRateIntensity(),
        drg.getWageShare()
    )) {
      throw new BadRequestException(DRG_NOT_CORRECT + drg);
    }
    if (!existsById(drg.getIdDrg())) {
      throw new NotFoundException(DRG_NOT_EXISTS + drg.getIdDrg());
    }
    return drgRepository.save(drg);
  }

  /**
   * Наличие {@link DiagnosisRelatedGroups}.
   *
   * @param idDrg id {@link DiagnosisRelatedGroups}
   * @return наличие
   */
  protected boolean existsById(final Long idDrg) {
    return drgRepository.existsById(idDrg);
  }

  /**
   * Удаление {@link DiagnosisRelatedGroups}.
   *
   * @param idDrg id {@link DiagnosisRelatedGroups}
   * @throws BadRequestException если id не корректный
   * @throws NotFoundException   если
   *                             {@link DiagnosisRelatedGroups} не найден
   */
  protected void deleteDrgById(final Long idDrg) {
    if (idDrg == null) {
      throw new BadRequestException(DRG_NOT_CORRECT);
    }
    if (!existsById(idDrg)) {
      throw new NotFoundException(DRG_NOT_EXISTS + idDrg);
    }
    drgRepository.deleteById(idDrg);
  }
}
