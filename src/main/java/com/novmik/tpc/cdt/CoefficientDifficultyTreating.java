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

/*
Таблица: Коэффициент сложности лечения пациентов
стационара (КСЛП) / CoefficientOfDifficultyInTreatingPatients
Колонки: Номер случая из БД КСЛП, Имя Субъекта РФ, Значение КСЛП
*/

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

  public CoefficientDifficultyTreating(final CaseCdt caseCdt,
      final String nameSubject,
      final Float valueNominationCaseCdt,
      final String careFacility) {
    this.caseCdt = caseCdt;
    this.nameSubject = nameSubject;
    this.valueNominationCaseCdt = valueNominationCaseCdt;
    this.careFacility = careFacility;
  }
}
