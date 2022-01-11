package com.novmik.tpc.medicament;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Цена на ЛП entity class.
 * (Лекарственный препарат)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "medicament_price")
public class MedicamentPrice {

  /**
   * id цены на ЛП.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idMp;
  /**
   * МНН.
   * (Международное непатентованное
   * наименование)
   */
  @Column(name = "inn")
  private String inn;
  /**
   * Выпускаемая дозировка ЛП.
   */
  @Column(name = "dosage")
  private Float dosage;
  /**
   * Цена ЛП с НДС.
   */
  @Column(name = "price_with_VAT")
  private Double priceWithVat;

  /**
   * Ctor.
   *
   * @param inn          МНН
   * @param dosage       выпускаемая дозировка ЛП
   * @param priceWithVat цена ЛП с НДС
   */
  public MedicamentPrice(final String inn, final Float dosage, final Double priceWithVat) {
    this.inn = inn;
    this.dosage = dosage;
    this.priceWithVat = priceWithVat;
  }
}
