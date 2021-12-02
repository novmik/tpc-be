package com.novmik.tpc.medicament;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class MedicamentPriceWithQuantityPackages extends MedicamentPrice {
    private Integer quantityPackage;

    public MedicamentPriceWithQuantityPackages(final Long id,
                                               final String inn,
                                               final Float dosage,
                                               final Double priceWithVAT,
                                               final Integer quantityPackage) {
        super(id, inn, dosage, priceWithVAT);
        this.quantityPackage = quantityPackage;
    }

    public static List<MedicamentPriceWithQuantityPackages> fromMedicamentPriceListToWithQuantityPackagesList(final List<MedicamentPrice> medicamentPriceList,
                                                                                                              final Integer quantityPackage) {
        return medicamentPriceList.stream().map(medicamentPrice -> new MedicamentPriceWithQuantityPackages(
                        medicamentPrice.getId(),
                        medicamentPrice.getInn(),
                        medicamentPrice.getDosage(),
                        medicamentPrice.getPriceWithVAT(),
                        quantityPackage)
                ).collect(Collectors.toList());
    }
}
