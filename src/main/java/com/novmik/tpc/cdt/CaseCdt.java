package com.novmik.tpc.cdt;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Случай сложности лечения пациентов стационара (КСЛП) / CaseCoefficientOfDifficultyInTreatingPatients
Колонки: Случаи, для которых установлен КСЛП
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "case_cdt")
public class CaseCdt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nomination_case_cdt")
    private String nominationCaseCdt;

    public CaseCdt(final String nominationCaseCdt) {
        this.nominationCaseCdt = nominationCaseCdt;
    }
}
