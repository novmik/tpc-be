package com.novmik.tpc.drg;

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
 * Перечень групп заболеваний entity class,
 * в том числе клинико-статистических (КСГ)
 * или клинико-профильных групп
 * заболеваний (КПГ)
 * с указанием коэффициентов
 * относительной затратоемкости КСГ или КПГ.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "diagnosis_related_groups")
public class DiagnosisRelatedGroups {

  /**
   * id КСГ.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idDrg;
  /**
   * КСГ.
   */
  @Column(name = "number_drg")
  private String numberDrg;
  /**
   * Наименование КСГ.
   */
  @Column(name = "nomination_drg")
  private String nominationDrg;
  /**
   * Кз.
   * (Коэффициент относительной
   * затратоемкости)
   */
  @Column(name = "rate_relative_intensity")
  private Float rateIntensity;
  /**
   * Дзп.
   * (Доля заработной платы)
   */
  @Column(name = "wage_share")
  private Float wageShare;

  /**
   * Ctor.
   *
   * @param numberDrg     КСГ
   * @param nominationDrg наименование КСГ
   * @param rateIntensity Кз
   * @param wageShare     Дзп
   */
  public DiagnosisRelatedGroups(final String numberDrg,
      final String nominationDrg,
      final Float rateIntensity,
      final Float wageShare) {
    this.numberDrg = numberDrg;
    this.nominationDrg = nominationDrg;
    this.rateIntensity = rateIntensity;
    this.wageShare = wageShare;
  }
}
