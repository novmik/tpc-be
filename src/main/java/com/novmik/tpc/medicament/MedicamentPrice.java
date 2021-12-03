package com.novmik.tpc.medicament;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
Таблица: Цена на лекарственные препараты
Колонки: МНН, дозировка, Цена руб. с НДС
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "medicament_price")
public class MedicamentPrice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "inn")
  private String inn;
  @Column(name = "dosage")
  private Float dosage;
  @Column(name = "price_with_VAT")
  private Double priceWithVat;

  public MedicamentPrice(final String inn, final Float dosage, final Double priceWithVat) {
    this.inn = inn;
    this.dosage = dosage;
    this.priceWithVat = priceWithVat;
  }
}
