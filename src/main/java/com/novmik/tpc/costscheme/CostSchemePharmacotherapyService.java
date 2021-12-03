package com.novmik.tpc.costscheme;

import static com.novmik.tpc.costscheme.CostSchemePharmacotherapyConstant.COST_REQUEST_INCORRECT;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.medicament.Medicament;
import com.novmik.tpc.medicament.MedicamentPrice;
import com.novmik.tpc.medicament.MedicamentPriceService;
import com.novmik.tpc.medicament.MedicamentPriceWithQuantityPackages;
import com.novmik.tpc.medicament.MedicamentService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CostSchemePharmacotherapyService {

  private final MedicamentService medicamentService;
  private final MedicamentPriceService medicamentPriceService;

  protected CostSchemePharmacotherapyResponse getCostSchemePharmacotherapy(
      final CostSchemePharmacotherapyRequest costSchemeRequest) {
    if (ObjectUtils.allNotNull(
        costSchemeRequest.getMedicamentList(),
        costSchemeRequest.getRegionalMarkup(),
        costSchemeRequest.getWeight(),
        costSchemeRequest.getBsa()
    )
        && costSchemeRequest.getRegionalMarkup() > 0
        && costSchemeRequest.getWeight() > 0
        && costSchemeRequest.getBsa() > 0
    ) {
      return getCostSchemePharmacotherapy(
          costSchemeRequest.getMedicamentList(),
          costSchemeRequest.getRegionalMarkup(),
          costSchemeRequest.getWeight(),
          costSchemeRequest.getBsa()
      );
    }
    if (ObjectUtils.allNotNull(
        costSchemeRequest.getCodeScheme(),
        costSchemeRequest.getRegionalMarkup(),
        costSchemeRequest.getWeight(),
        costSchemeRequest.getBsa()
    )
        && StringUtils.isNotBlank(costSchemeRequest.getCodeScheme())
        && costSchemeRequest.getRegionalMarkup() > 0
        && costSchemeRequest.getWeight() > 0
        && costSchemeRequest.getBsa() > 0
    ) {
      return getCostSchemePharmacotherapy(
          costSchemeRequest.getCodeScheme(),
          costSchemeRequest.getRegionalMarkup(),
          costSchemeRequest.getWeight(),
          costSchemeRequest.getBsa()
      );
    }
    throw new BadRequestException(COST_REQUEST_INCORRECT + costSchemeRequest);
  }

  private CostSchemePharmacotherapyResponse getCostSchemePharmacotherapy(
      final String codeScheme,
      final Double regionalMarkup,
      final Double weight,
      final Double bsa
  ) {
    List<Medicament> medicamentList = medicamentService.getMedicamentListBySchemePharmacotherapy(
        codeScheme);
    return getCostSchemePharmacotherapy(
        medicamentList,
        regionalMarkup,
        weight,
        bsa
    );
  }

  private CostSchemePharmacotherapyResponse getCostSchemePharmacotherapy(
      final List<Medicament> medicamentList,
      final Double regionalMarkup,
      final Double weight,
      final Double bsa
  ) {
    List<MedicamentPriceWithQuantityPackages> listAllMedicamentPriceWithQuantityPackages =
        new ArrayList<>();
    BigDecimal costScheme = BigDecimal.ZERO;
    for (Medicament medicament : medicamentList) {
      BigDecimal requiredDose = CalculatorDosage.getRequiredDose(medicament, weight, bsa);
      medicament.setRequiredDose(requiredDose);
      List<MedicamentPrice> medicalPriceList = medicamentPriceService.getMedicalPriceList(
          StringUtils.capitalize(medicament.getInnMedicament()));
      medicalPriceList = medicalPriceList.stream()
          .sorted(Comparator.comparing(MedicamentPrice::getDosage).reversed()).toList();
      List<MedicamentPriceWithQuantityPackages> medicamentPriceWithZeroQuantityPackagesList =
          MedicamentPriceWithQuantityPackages.fromMedicamentPriceToWithQuantityPackages(
              medicalPriceList, 0);
      listAllMedicamentPriceWithQuantityPackages.addAll(
          medicamentPriceWithZeroQuantityPackagesList);
      List<Float> dosagesMedicamentList = medicamentPriceWithZeroQuantityPackagesList
          .stream()
          .map(MedicamentPrice::getDosage)
          .sorted(Comparator.reverseOrder())
          .collect(Collectors.toList());
      SortedMap<Double, Map<Float, Integer>> mapResidual = CalculatorResidual.getMapResidual(
          dosagesMedicamentList, requiredDose);
      Map<Float, Integer> requiredDoseQuantityPackageMap = mapResidual.get(mapResidual.firstKey());
      for (Float dose : requiredDoseQuantityPackageMap.keySet()) {
        Integer quantityPackage = requiredDoseQuantityPackageMap.get(dose);
        Optional<MedicamentPriceWithQuantityPackages> optionalMedicamentPriceWithQuantityPackages =
            medicamentPriceWithZeroQuantityPackagesList
                .stream()
                .filter(medPrice -> medPrice.getDosage().equals(dose))
                .findFirst();
        if (optionalMedicamentPriceWithQuantityPackages.isEmpty()) {
          costScheme = BigDecimal.ZERO;
          break;
        }
        optionalMedicamentPriceWithQuantityPackages.get().setQuantityPackage(quantityPackage);
        Double priceWithVat = optionalMedicamentPriceWithQuantityPackages.get().getPriceWithVat();
        costScheme = costScheme.add(BigDecimal
            .valueOf(quantityPackage * priceWithVat * regionalMarkup)
            .setScale(5, RoundingMode.HALF_UP));
      }

    }
    return new CostSchemePharmacotherapyResponse(
        costScheme.setScale(2, RoundingMode.HALF_UP),
        medicamentList,
        listAllMedicamentPriceWithQuantityPackages
    );
  }
}
