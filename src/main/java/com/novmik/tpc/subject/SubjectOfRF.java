package com.novmik.tpc.subject;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Субъект РФ
Колонки: Субъект, Базовая ставка круглосуточного стационара, Базовая ставка дневного стационара
(размер средней стоимости законченного случая лечения без учета коэффициента дифференциации)
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "subject_of_rf")
public class SubjectOfRF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_subject")
    private String nameSubject;
    @Column(name = "baserate_rtccf")
    private Double baseRateRoundTheClockCareFacility;
    @Column(name = "baserate_dcf")
    private Double baseRateDayCareFacility;

    public SubjectOfRF(String nameSubject, Double baseRateRoundTheClockCareFacility, Double baseRateDayCareFacility) {
        this.nameSubject = nameSubject;
        this.baseRateRoundTheClockCareFacility = baseRateRoundTheClockCareFacility;
        this.baseRateDayCareFacility = baseRateDayCareFacility;
    }
}