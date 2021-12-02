package com.novmik.tpc.drg;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Перечень групп заболеваний, в том числе клинико-статистических (КСГ) или клинико-профильных групп заболеваний (КПГ)
 с указанием коэффициентов  относительной затратоемкости КСГ или КПГ
Колонки: КСГ, Наименование КСГ, Коэффициент относительной затратоемкости (Кз), Доля заработной платы (Дзп)
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "diagnosis_related_groups")
public class DiagnosisRelatedGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_drg")
    private String numberDrg;
    @Column(name = "nomination_drg")
    private String nominationDrg;
    @Column(name = "rate_relative_intensity")
    private Float rateRelativeIntensity;
    @Column(name = "wage_share")
    private Float wageShare;

    public DiagnosisRelatedGroups(final String numberDrg,
                                  final String nominationDrg,
                                  final Float rateRelativeIntensity,
                                  final Float wageShare) {
        this.numberDrg = numberDrg;
        this.nominationDrg = nominationDrg;
        this.rateRelativeIntensity = rateRelativeIntensity;
        this.wageShare = wageShare;
    }
}
