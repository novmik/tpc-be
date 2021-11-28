package com.novmik.tpc.medicalinstitution;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Медицинская организация (МО)
Колонки: Наименование медицинской организации, Коэффициент дифференциации,
Уровень МО КС, Коэффициент уровня медицинской организации КС (КУС КС), Уровень МО ДС, Коэффициент уровня медицинской организации ДС (КУС ДС), Тип МО
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "medical_institution")
public class MedicalInstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_subject")
    private String nameSubject;
    @Column(name = "name_mi")
    private String nameMedicalInstitution;
    @Column(name = "differentiation_coefficient")
    private Float differentiationCoefficient;
    @Column(name = "level_mo_rtccf")
    private String levelMoRTCCF;
    @Column(name = "coefficient_level_mo_rtccf")
    private Float coefficientOfLevel_MO_rtccf;
    @Column(name = "level_mo_dcf")
    private String levelMoDCF;
    @Column(name = "coefficient_level_mo_dcf")
    private Float coefficientOfLevel_MO_dcf;
    @Column(name = "type_medical_institution")
    private Integer typeMedicalInstitution;

    public MedicalInstitution(String nameSubject, String nameMedicalInstitution, Float differentiationCoefficient, String levelMoRTCCF, Float coefficientOfLevel_MO_rtccf, String levelMoDCF, Float coefficientOfLevel_MO_dcf, Integer typeMedicalInstitution) {
        this.nameSubject = nameSubject;
        this.nameMedicalInstitution = nameMedicalInstitution;
        this.differentiationCoefficient = differentiationCoefficient;
        this.levelMoRTCCF = levelMoRTCCF;
        this.coefficientOfLevel_MO_rtccf = coefficientOfLevel_MO_rtccf;
        this.levelMoDCF = levelMoDCF;
        this.coefficientOfLevel_MO_dcf = coefficientOfLevel_MO_dcf;
        this.typeMedicalInstitution = typeMedicalInstitution;
    }
}
