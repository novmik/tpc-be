package com.novmik.tpc.medicalinstitution;

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
 * МО entity class.
 * Медицинская организация
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "medical_institution")
@SuppressWarnings("PMD.CommentSize")
public class MedicalInstitution {

  /**
   * id Медицинской организации.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idMi;
  /**
   * Наименование субъекта РФ.
   */
  @Column(name = "name_subject")
  private String nameSubject;
  /**
   * Наименование медицинской организации.
   */
  @Column(name = "name_mi")
  private String nameMi;
  /**
   * КД.
   * Коэффициент дифференциации
   */
  @Column(name = "differentiation_coefficient")
  private Float diffCoefficient;
  /**
   * Уровень МО КС.
   * Уровень медицинской организации
   * круглосуточного стационара
   */
  @Column(name = "level_st")
  private String levelSt;
  /**
   * КУС КС.
   * Коэффициент уровня
   * медицинской организации
   * круглосуточного стационара
   */
  @Column(name = "coefficient_level_st")
  private Float coefficientSt;
  /**
   * Уровень МО КС.
   * Уровень медицинской организации
   * дневного стационара
   */
  @Column(name = "level_ds")
  private String levelDs;
  /**
   * КУС ДС.
   * Коэффициент уровня
   * медицинской организации
   * дневного стационара
   */
  @Column(name = "coefficient_level_ds")
  private Float coefficientDs;
  /**
   * Тип МО.
   * Тип медицинской организации
   * {@link com.novmik.tpc.typemedicalinstitution.TypeMedicalInstitution}
   */
  @Column(name = "type_medical_institution")
  private Integer typeMi;

  /**
   * Ctor.
   *
   * @param nameSubject наименование субъекта РФ
   * @param nameMi наименование МО
   * @param diffCoefficient КД
   * @param levelSt Уровень МО КС
   * @param coefficientSt КУС КС
   * @param levelDs Уровень МО ДС
   * @param coefficientDs КУС ДС
   * @param typeMi тип МО
   */
  public MedicalInstitution(final String nameSubject,
      final String nameMi,
      final Float diffCoefficient,
      final String levelSt,
      final Float coefficientSt,
      final String levelDs,
      final Float coefficientDs,
      final Integer typeMi) {
    this.nameSubject = nameSubject;
    this.nameMi = nameMi;
    this.diffCoefficient = diffCoefficient;
    this.levelSt = levelSt;
    this.coefficientSt = coefficientSt;
    this.levelDs = levelDs;
    this.coefficientDs = coefficientDs;
    this.typeMi = typeMi;
  }
}
