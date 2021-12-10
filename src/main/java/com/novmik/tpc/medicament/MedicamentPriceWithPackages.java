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
   * Ctor.
   *
   * @param idMp            id {@link MedicamentPrice}
   * @param inn             МНН
   * @param dosage          дозировка ЛП
   * @param priceWithVat    цена ЛП с НДС
   * @param quantityPackage кол-во упаковок ЛП
   */
  public MedicamentPriceWithPackages(final Long idMp,
      final String inn,
      final Float dosage,
      final Double priceWithVat,
      final Integer quantityPackage) {
    super(idMp, inn, dosage, priceWithVat);
    this.quantityPackage = quantityPackage;
  }

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
            medicamentPrice.getIdMp(),
            medicamentPrice.getInn(),
            medicamentPrice.getDosage(),
            medicamentPrice.getPriceWithVat(),
            quantityPackage)
        ).collect(Collectors.toList());
  }
}
