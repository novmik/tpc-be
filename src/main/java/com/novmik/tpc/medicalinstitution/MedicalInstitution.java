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

/*
Таблица: Медицинская организация (МО)
Колонки: Наименование медицинской организации,
Коэффициент дифференциации, Уровень МО КС,
Коэффициент уровня медицинской организации КС (КУС КС),
Уровень МО ДС, Коэффициент уровня медицинской организации ДС (КУС ДС), Тип МО
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "medical_institution")
public class MedicalInstitution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name_subject")
  private String nameSubject;
  @Column(name = "name_mi")
  private String nameMedicalInstitution;
  @Column(name = "differentiation_coefficient")
  private Float differentiationCoefficient;
  @Column(name = "level_mo_rtccf")
  private String levelMoRtccf;
  @Column(name = "coefficient_level_mo_rtccf")
  private Float coefficientLevelMoRtccf;
  @Column(name = "level_mo_dcf")
  private String levelMoDcf;
  @Column(name = "coefficient_level_mo_dcf")
  private Float coefficientLevelMoDcf;
  @Column(name = "type_medical_institution")
  private Integer typeMedicalInstitution;

  public MedicalInstitution(final String nameSubject,
      final String nameMedicalInstitution,
      final Float differentiationCoefficient,
      final String levelMoRtccf,
      final Float coefficientLevelMoRtccf,
      final String levelMoDcf,
      final Float coefficientLevelMoDcf,
      final Integer typeMedicalInstitution) {
    this.nameSubject = nameSubject;
    this.nameMedicalInstitution = nameMedicalInstitution;
    this.differentiationCoefficient = differentiationCoefficient;
    this.levelMoRtccf = levelMoRtccf;
    this.coefficientLevelMoRtccf = coefficientLevelMoRtccf;
    this.levelMoDcf = levelMoDcf;
    this.coefficientLevelMoDcf = coefficientLevelMoDcf;
    this.typeMedicalInstitution = typeMedicalInstitution;
  }
}
