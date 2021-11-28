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

    public MedicamentPriceWithQuantityPackages(Long id, String inn, Float dosage, Double priceWithVAT, Integer quantityPackage) {
        super(id, inn, dosage, priceWithVAT);
        this.quantityPackage = quantityPackage;
    }

    public static List<MedicamentPriceWithQuantityPackages> fromMedicamentPriceListToWithQuantityPackagesList(List<MedicamentPrice> medicamentPriceList, Integer quantityPackage) {
        return medicamentPriceList.stream().map(medicamentPrice -> new MedicamentPriceWithQuantityPackages(
                        medicamentPrice.getId(),
                        medicamentPrice.getInn(),
                        medicamentPrice.getDosage(),
                        medicamentPrice.getPriceWithVAT(),
                        quantityPackage)
                ).collect(Collectors.toList());
    }
}
