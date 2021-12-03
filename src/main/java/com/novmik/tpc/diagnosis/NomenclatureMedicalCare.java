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

/*
Таблица: Номенклатура медицинских услуг
Колонки: Название медицинской услуги, Код медицинской услуги
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "nomenclature_medical_care")
public class NomenclatureMedicalCare {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "code_medical_care")
  private String codeMedicalCara;
  @Column(name = "name_medical_care")
  private String nameMedicalCare;

  public NomenclatureMedicalCare(final String codeMedicalCara, final String nameMedicalCare) {
    this.codeMedicalCara = codeMedicalCara;
    this.nameMedicalCare = nameMedicalCare;
  }
}
