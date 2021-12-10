package com.novmik.tpc.medicament;

import static com.novmik.tpc.medicament.MedicamentConstants.MEDICAMENT_LIST_ERROR;

import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapyService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * {@link Medicament} business interface layer.
 */
@AllArgsConstructor
@Service
public class MedicamentService {

  /**
   * {@link SchemePharmacotherapyService}.
   */
  private final SchemePharmacotherapyService schemeService;

  /**
   * Список {@link Medicament} по коду СЛТ.
   *
   * @param codeScheme Код СЛТ {@link SchemePharmacotherapy}
   * @return список {@link Medicament}
   */
  public List<Medicament> getMedicamentListBySchemePharmacotherapy(final String codeScheme) {
    final SchemePharmacotherapy byCodeScheme = schemeService.findByCodeScheme(codeScheme)
        .orElseThrow();
    return getMedicamentListBySchemePharmacotherapy(byCodeScheme);
  }

  private List<Medicament> getMedicamentListBySchemePharmacotherapy(
      final SchemePharmacotherapy scheme) {
    final List<Medicament> medicamentList = MedicamentExtractorResolverUtils.getMedicamentList(
        scheme);
    if (medicamentList.isEmpty()) {
      throw new NotFoundException(MEDICAMENT_LIST_ERROR + scheme);
    }
    return medicamentList;
  }

}
