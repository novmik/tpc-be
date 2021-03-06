package com.novmik.tpc.costscheme;

import com.novmik.tpc.bsa.BodySurfaceAreaUtils;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPrice;
import com.novmik.tpc.medicament.MedicamentPriceService;
import com.novmik.tpc.medicament.MedicamentPriceWithPackages;
import com.novmik.tpc.medicament.MedicamentService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedMap;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Стоимость СЛТ business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings({"PMD.LawOfDemeter",
    "PMD.CommentSize",
    "DataflowAnomalyAnalysis"})
public class CostSchemePharmacotherapyService {

  /**
   * {@link MedicamentService}.
   */
  private final MedicamentService medicamentService;
  /**
   * {@link MedicamentPriceService}.
   */
  private final MedicamentPriceService medPriceService;

  /**
   * Стоимость СЛТ.
   *
   * @param costSchemeRequest {@link CostSchemePharmacotherapyRequest}
   * @return {@link CostSchemePharmacotherapyResponse}
   * @throws BadRequestException если некорректные данные
   */
  protected CostSchemePharmacotherapyResponse getCostSchemePharmacotherapy(
      final CostSchemePharmacotherapyRequest costSchemeRequest) {
    CostSchemePharmacotherapyResponse costResponse;
    if (ObjectUtils.allNotNull(costSchemeRequest.getMedicamentList())
        && checkingRequestParameters(costSchemeRequest)
    ) {
      costResponse = getCostSchemePharmacotherapy(
          costSchemeRequest.getMedicamentList(),
          costSchemeRequest.getRegionalMarkup(),
          costSchemeRequest.getWeight(),
          costSchemeRequest.getBsa()
      );
    } else if (ObjectUtils.allNotNull(costSchemeRequest.getCodeScheme())
        && StringUtils.isNotBlank(costSchemeRequest.getCodeScheme())
        && checkingRequestParameters(costSchemeRequest)
    ) {
      costResponse = getCostSchemePharmacotherapy(
          costSchemeRequest.getCodeScheme(),
          costSchemeRequest.getRegionalMarkup(),
          costSchemeRequest.getWeight(),
          costSchemeRequest.getBsa()
      );
    } else {
      throw new BadRequestException(
          "Не верный запрос стоимости схемы лекарственной терапии: " + costSchemeRequest);
    }
    return costResponse;
  }

  /**
   * Стоимость СЛТ.
   *
   * @param codeScheme     код СЛТ
   * @param regionalMarkup региональная наценка ЛП
   * @param weight         вес человека, кг
   * @param bsa            ППТ {@link BodySurfaceAreaUtils}
   * @return {@link CostSchemePharmacotherapyResponse}
   */
  private CostSchemePharmacotherapyResponse getCostSchemePharmacotherapy(
      final String codeScheme,
      final Double regionalMarkup,
      final Double weight,
      final Double bsa
  ) {
    final List<Medicament> medicamentList = medicamentService
        .getMedicamentListBySchemePharmacotherapy(codeScheme);
    return getCostSchemePharmacotherapy(
        medicamentList,
        regionalMarkup,
        weight,
        bsa
    );
  }

  /**
   * Стоимость СЛТ.
   *
   * @param medicamentList {@link Medicament}
   * @param regionalMarkup региональная наценка ЛП
   * @param weight         вес человека, кг
   * @param bsa            ППТ {@link BodySurfaceAreaUtils}
   * @return {@link CostSchemePharmacotherapyResponse}
   */
  private CostSchemePharmacotherapyResponse getCostSchemePharmacotherapy(
      final List<Medicament> medicamentList,
      final Double regionalMarkup,
      final Double weight,
      final Double bsa
  ) {
    final List<MedicamentPriceWithPackages> medsPrice = new ArrayList<>();
    BigDecimal costScheme = BigDecimal.ZERO;
    for (final Medicament medicament : medicamentList) {
      final BigDecimal requiredDose =
          CalculatorDosageUtils.getRequiredDose(medicament, weight, bsa);
      medicament.setRequiredDose(requiredDose);
      List<MedicamentPrice> medicalPriceList = medPriceService.getMedicalPriceList(
          StringUtils.capitalize(medicament.getInnMedicament()));
      medicalPriceList = medicalPriceList
          .stream()
          .sorted(Comparator.comparing(MedicamentPrice::getDosage).reversed())
          .toList();
      final List<MedicamentPriceWithPackages> medsPriceTemp =
          MedicamentPriceWithPackages
              .fromMedicamentPriceToWithQuantityPackages(medicalPriceList, 0);
      medsPrice.addAll(medsPriceTemp);
      final List<Float> dosagesMeds = medsPriceTemp
          .stream()
          .map(MedicamentPrice::getDosage)
          .sorted(Comparator.reverseOrder())
          .collect(Collectors.toList());
      final SortedMap<Double, Map<Float, Integer>> mapResidual = CalculatorResidualUtils
          .getMapResidual(dosagesMeds, requiredDose);
      final Map<Float, Integer> dosePackage = mapResidual.get(mapResidual.firstKey());
      for (final Entry<Float, Integer> entry : dosePackage.entrySet()) {
        final Optional<MedicamentPriceWithPackages> oMedsPrice =
            medsPriceTemp
                .stream()
                .filter(medPrice -> medPrice.getDosage().equals(entry.getKey()))
                .findFirst();
        if (oMedsPrice.isEmpty()) {
          costScheme = BigDecimal.ZERO;
          break;
        }
        final int quantityPackage = entry.getValue();
        oMedsPrice.get().setQuantityPackage(quantityPackage);
        final Double priceWithVat = oMedsPrice.get().getPriceWithVat();
        costScheme = costScheme.add(BigDecimal
            .valueOf(quantityPackage * priceWithVat * regionalMarkup)
            .setScale(5, RoundingMode.HALF_UP));
      }
    }
    return new CostSchemePharmacotherapyResponse(
        costScheme.setScale(2, RoundingMode.HALF_UP),
        medicamentList,
        medsPrice
    );
  }

  private boolean checkingRequestParameters(
      final CostSchemePharmacotherapyRequest costSchemeRequest) {
    return ObjectUtils.allNotNull(
        costSchemeRequest.getRegionalMarkup(),
        costSchemeRequest.getWeight(),
        costSchemeRequest.getBsa()
    ) && costSchemeRequest.getRegionalMarkup() > 0
        && costSchemeRequest.getWeight() > 0
        && costSchemeRequest.getBsa() > 0;
  }
}
