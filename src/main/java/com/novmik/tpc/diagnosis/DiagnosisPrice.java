package com.novmik.tpc.diagnosis;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Цена на медицинскую диагностику
Колонки: Субъект, Номенклатура медицинских услуг, Стоимость диагностики
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "diagnosis_price")
public class DiagnosisPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_subject")
    private String nameSubject;
    @OneToOne
    @JoinColumn(name = "nomenclature_medical_care_id")
    private NomenclatureMedicalCare nomenclatureMedicalCare;
    @Column(name = "price_medical_care")
    private Double priceMedicalCare;

    public DiagnosisPrice(String nameSubject, NomenclatureMedicalCare nomenclatureMedicalCare, Double priceMedicalCare) {
        this.nameSubject = nameSubject;
        this.nomenclatureMedicalCare = nomenclatureMedicalCare;
        this.priceMedicalCare = priceMedicalCare;
    }
}
