package com.novmik.tpc.schemepharmacotherapy;

import lombok.*;

import javax.persistence.*;

/*
Таблица: Схема лекарственной терапии
Колонки:
    Код схемы лекарственной терапии
    МНН лекарственных препаратов, входящих в состав схемы
    Наименование и описание схемы: Длительность цикла лекарственной терапии, режим дозирования и способ введения лекарственных препаратов, Количество дней введения лекарственных препаратов, оплачиваемых по КСГ)
    Номер КСГ, к которой может быть отнесена схема лекарственной терапии
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "scheme_pharmacotherapy")
public class SchemePharmacotherapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_scheme")
    private String codeScheme;
    @Column(name = "inn_medicament")
    private String innMedicament;
    @Column(name = "name_and_description_scheme")
    private String nameAndDescriptionScheme;
    @Column(name = "number_days_drug_treatments")
    private Integer numberDaysDrugTreatments;
    @Column(name = "number_drg_rttcf")
    private String numberDrgRtccf;
    @Column(name = "number_drg_dcf")
    private String numberDrgDcf;

    public SchemePharmacotherapy(final String codeScheme,
                                 final String innMedicament,
                                 final String nameAndDescriptionScheme,
                                 final Integer numberDaysDrugTreatments,
                                 final String numberDrgRtccf,
                                 final String numberDrgDcf) {
        this.codeScheme = codeScheme;
        this.innMedicament = innMedicament;
        this.nameAndDescriptionScheme = nameAndDescriptionScheme;
        this.numberDaysDrugTreatments = numberDaysDrugTreatments;
        this.numberDrgRtccf = numberDrgRtccf;
        this.numberDrgDcf = numberDrgDcf;
    }
}
