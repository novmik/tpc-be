package com.novmik.tpc.diagnosis;

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
 * Номенклатура медицинских услуг entity class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "nomenclature_medical_care")
public class NomenclatureMedicalCare {

  /**
   * id номенклатуры медицинских услуг.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer idNmc;
  /**
   * Код медицинской услуги.
   */
  @Column(name = "code_medical_care")
  private String codeMedicalCare;
  /**
   * Название медицинской услуги.
   */
  @Column(name = "name_medical_care")
  private String nameMedicalCare;

  /**
   * Ctor.
   *
   * @param codeMedicalCare код медицинской услуги
   * @param nameMedicalCare название медицинской услуги
   */
  public NomenclatureMedicalCare(final String codeMedicalCare, final String nameMedicalCare) {
    this.codeMedicalCare = codeMedicalCare;
    this.nameMedicalCare = nameMedicalCare;
  }
}
