package com.novmik.tpc.cdt;

/*
Таблица: Коэффициент сложности лечения пациентов стационара (КСЛП) / CoefficientOfDifficultyInTreatingPatients
Колонки: Номер случая из БД КСЛП, Имя Субъекта РФ, Значение КСЛП
*/

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "coefficient_difficulty_treating")
public class CoefficientDifficultyTreating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "nomination_case_cdt_id")
    private CaseCdt caseCdt;
    @Column(name = "name_subject")
    private String nameSubject;
    @Column(name = "value_nomination_case_cdt")
    private Float valueNominationCaseCdt;
    @Column(name = "care_facility")
    private String careFacility;

    public CoefficientDifficultyTreating(CaseCdt caseCdt, String nameSubject, Float valueNominationCaseCdt, String careFacility) {
        this.caseCdt = caseCdt;
        this.nameSubject = nameSubject;
        this.valueNominationCaseCdt = valueNominationCaseCdt;
        this.careFacility = careFacility;
    }
}
