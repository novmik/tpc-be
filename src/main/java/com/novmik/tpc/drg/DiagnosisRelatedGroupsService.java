package com.novmik.tpc.drg;

import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_EXISTS;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_NOT_CORRECT;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_NOT_EXISTS;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_NOT_EXISTS_BY_NAME_DRG;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DiagnosisRelatedGroupsService {

  private final DiagnosisRelatedGroupsRepository drgRepository;

  public Optional<DiagnosisRelatedGroups> byNumberDrg(final String drg) {
    Optional<DiagnosisRelatedGroups> byNumberDrg = drgRepository.findByNumberDrg(drg);
    if (byNumberDrg.isEmpty()) {
      throw new NotFoundException(DRG_NOT_EXISTS_BY_NAME_DRG + drg);
    }
    return byNumberDrg;
  }

  protected DiagnosisRelatedGroups addNewDrg(final DiagnosisRelatedGroups diagnosisRelatedGroups) {
    if (ObjectUtils.anyNull(
        diagnosisRelatedGroups,
        diagnosisRelatedGroups.getNumberDrg(),
        diagnosisRelatedGroups.getNominationDrg(),
        diagnosisRelatedGroups.getRateRelativeIntensity(),
        diagnosisRelatedGroups.getWageShare()
    )) {
      throw new BadRequestException(DRG_NOT_CORRECT + diagnosisRelatedGroups);
    }
    if (drgRepository.findByNumberDrg(diagnosisRelatedGroups.getNumberDrg()).isPresent()) {
      throw new BadRequestException(DRG_EXISTS + diagnosisRelatedGroups.getNumberDrg());
    }
    return drgRepository.save(diagnosisRelatedGroups);
  }

  protected DiagnosisRelatedGroups updateDrg(final DiagnosisRelatedGroups diagnosisRelatedGroups) {
    if (ObjectUtils.anyNull(
        diagnosisRelatedGroups,
        diagnosisRelatedGroups.getId(),
        diagnosisRelatedGroups.getNumberDrg(),
        diagnosisRelatedGroups.getNominationDrg(),
        diagnosisRelatedGroups.getRateRelativeIntensity(),
        diagnosisRelatedGroups.getWageShare()
    )) {
      throw new BadRequestException(DRG_NOT_CORRECT + diagnosisRelatedGroups);
    }
    if (!existsById(diagnosisRelatedGroups.getId())) {
      throw new NotFoundException(DRG_NOT_EXISTS + diagnosisRelatedGroups.getId());
    }
    return drgRepository.save(diagnosisRelatedGroups);
  }

  protected boolean existsById(final Long id) {
    return drgRepository.existsById(id);
  }

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
