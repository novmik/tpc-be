package com.novmik.tpc.diagnosis;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Номенклатура медицинских услуг
Колонки: Название медицинской услуги, Код медицинской услуги
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "nomenclature_medical_care")
public class NomenclatureMedicalCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code_medical_care")
    private String codeMedicalCara;
    @Column(name = "name_medical_care")
    private String nameMedicalCare;

    public NomenclatureMedicalCare(String codeMedicalCara, String nameMedicalCare) {
        this.codeMedicalCara = codeMedicalCara;
        this.nameMedicalCare = nameMedicalCare;
    }
}
