package com.novmik.tpc.medicament;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link MedicamentPrice}
 * с необходимым кол-ом
 * упаковок.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentSize"})
public class MedicamentPriceWithPackages extends MedicamentPrice {

  /**
   * Кол-во упаковок ЛП.
   */
  private Integer quantityPackage;

  /**
   * Список ЛП с кол-ом упаковок.
   *
   * @param medicamentsPrice список {@link MedicamentPrice}
   * @param quantityPackage  кол-во упаковок ЛП
   * @return список {@link MedicamentPriceWithPackages}
   */
  public static List<MedicamentPriceWithPackages> fromMedicamentPriceToWithQuantityPackages(
      final List<MedicamentPrice> medicamentsPrice,
      final Integer quantityPackage) {
    return medicamentsPrice.stream()
        .map(medicamentPrice -> new MedicamentPriceWithPackages(
            medicamentPrice,
            quantityPackage)
        ).collect(Collectors.toList());
  }

  /**
   * Ctor.
   *
   * @param medicamentPrice {@link MedicamentPrice}
   * @param quantityPackage кол-во упаковок ЛП
   */
  public MedicamentPriceWithPackages(
      final MedicamentPrice medicamentPrice,
      final Integer quantityPackage) {
    super(
        medicamentPrice.getIdMp(),
        medicamentPrice.getInn(),
        medicamentPrice.getDosage(),
        medicamentPrice.getPriceWithVat()
    );
    this.quantityPackage = quantityPackage;
  }
}
