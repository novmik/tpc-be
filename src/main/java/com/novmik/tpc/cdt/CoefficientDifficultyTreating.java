package com.novmik.tpc.cdt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * КСЛП.
 * (Коэффициент сложности лечения
 * пациентов стационара)
 * Coefficient Of Difficulty
 * In Treating Patients
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "coefficient_difficulty_treating")
public class CoefficientDifficultyTreating {

  /**
   * id КСЛП.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idCdt;
  /**
   * Случай КСЛП.
   */
  @OneToOne
  @JoinColumn(name = "nomination_case_cdt_id")
  private CaseCdt caseCdt;
  /**
   * Наименование субъекта РФ.
   */
  @Column(name = "name_subject")
  private String nameSubject;
  /**
   * Значение КСЛП.
   */
  @Column(name = "value_nomination_case_cdt")
  private Float value;
  /**
   * Стационар.
   * (Круглосуточный или Дневной)
   */
  @Column(name = "care_facility")
  private String careFacility;

  /**
   * Ctor.
   *
   * @param caseCdt сучай КСЛП {@link CaseCdt}
   * @param nameSubject имя субъекта РФ
   * @param value значение КСЛП
   * @param careFacility стационар
   */
  public CoefficientDifficultyTreating(final CaseCdt caseCdt,
      final String nameSubject,
      final Float value,
      final String careFacility) {
    this.caseCdt = caseCdt;
    this.nameSubject = nameSubject;
    this.value = value;
    this.careFacility = careFacility;
  }
}
