package com.novmik.tpc.medicament;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class MedicamentPriceWithQuantityPackages extends MedicamentPrice {

  private Integer quantityPackage;

  public MedicamentPriceWithQuantityPackages(final Long id,
      final String inn,
      final Float dosage,
      final Double priceWithVat,
      final Integer quantityPackage) {
    super(id, inn, dosage, priceWithVat);
    this.quantityPackage = quantityPackage;
  }

  public static List<MedicamentPriceWithQuantityPackages> fromMedicamentPriceToWithQuantityPackages(
      final List<MedicamentPrice> medicamentPriceList,
      final Integer quantityPackage) {
    return medicamentPriceList.stream()
        .map(medicamentPrice -> new MedicamentPriceWithQuantityPackages(
            medicamentPrice.getId(),
            medicamentPrice.getInn(),
            medicamentPrice.getDosage(),
            medicamentPrice.getPriceWithVat(),
            quantityPackage)
        ).collect(Collectors.toList());
  }
}
