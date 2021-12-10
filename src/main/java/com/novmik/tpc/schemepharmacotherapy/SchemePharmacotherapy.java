package com.novmik.tpc.schemepharmacotherapy;

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

/**
 * Схема лекарственной терапии entity class.
 * (СЛТ)
 */
@SuppressWarnings("PMD.CommentSize")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "scheme_pharmacotherapy")
public class SchemePharmacotherapy {

  /**
   * id схемы лекарственной терапии.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idSp;
  /**
   * Код СЛТ.
   * (схема лекарственной терапии)
   */
  @Column(name = "code_scheme")
  private String codeScheme;
  /**
   * МНН лекарственных препаратов.
   * Входящих в состав схемы
   */
  @Column(name = "inn_medicament")
  private String innMedicament;
  /**
   * Наименование и описание схемы.
   * Длительность цикла лекарственной терапии,
   * режим дозирования и способ
   * введения лекарственных препаратов
   */
  @Column(name = "name_and_description_scheme")
  private String description;
  /**
   * Кол-во дней введения.
   * Лекарственных препаратов,
   * оплачиваемых по КСГ
   */
  @Column(name = "number_days_drug_treatments")
  private Integer daysTreatments;
  /**
   * Номер КСГ круглосуточного стационара.
   * К которой может быть отнесена
   * схема лекарственной терапии
   */
  @Column(name = "number_drg_rttcf")
  private String numberDrgSt;
  /**
   * Номер КСГ дневного стационара.
   * К которой может быть отнесена
   * схема лекарственной терапии
   */
  @Column(name = "number_drg_dcf")
  private String numberDrgDs;

  /**
   * Ctor.
   *
   * @param codeScheme код схемы
   * @param innMedicament МНН ЛП`ов
   * @param description Наименование и описание схемы
   * @param daysTreatments Кол-во дней введения ЛП
   * @param numberDrgSt Номер КСГ КС
   * @param numberDrgDs Номер КСГ ДС
   */
  public SchemePharmacotherapy(final String codeScheme,
      final String innMedicament,
      final String description,
      final Integer daysTreatments,
      final String numberDrgSt,
      final String numberDrgDs) {
    this.codeScheme = codeScheme;
    this.innMedicament = innMedicament;
    this.description = description;
    this.daysTreatments = daysTreatments;
    this.numberDrgSt = numberDrgSt;
    this.numberDrgDs = numberDrgDs;
  }
}
