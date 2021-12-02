package com.novmik.tpc.medicament;

import lombok.*;

import javax.persistence.*;

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
    private Double priceWithVAT;

    public MedicamentPrice(final String inn, final Float dosage, final Double priceWithVAT) {
        this.inn = inn;
        this.dosage = dosage;
        this.priceWithVAT = priceWithVAT;
    }
}
