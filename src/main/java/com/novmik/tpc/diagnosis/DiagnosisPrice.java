package com.novmik.tpc.diagnosis;

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
Таблица: Цена на медицинскую диагностику
Колонки: Субъект, Номенклатура медицинских услуг, Стоимость диагностики
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "diagnosis_price")
public class DiagnosisPrice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name_subject")
  private String nameSubject;
  @OneToOne
  @JoinColumn(name = "nomenclature_medical_care_id")
  private NomenclatureMedicalCare nomenclatureMedicalCare;
  @Column(name = "price_medical_care")
  private Double priceMedicalCare;

  public DiagnosisPrice(final String nameSubject,
      final NomenclatureMedicalCare nomenclatureMedicalCare,
      final Double priceMedicalCare) {
    this.nameSubject = nameSubject;
    this.nomenclatureMedicalCare = nomenclatureMedicalCare;
    this.priceMedicalCare = priceMedicalCare;
  }
}
