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

/**
 * Цена на медицинскую диагностику entity class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "diagnosis_price")
public class DiagnosisPrice {

  /**
   * id цены на медицинскую диагностику.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idDp;
  /**
   * Наименование субъекта РФ.
   */
  @Column(name = "name_subject")
  private String nameSubject;
  /**
   * {@link NomenclatureMedicalCare}.
   */
  @OneToOne
  @JoinColumn(name = "nomenclature_medical_care_id")
  private NomenclatureMedicalCare nomenclatureMc;
  /**
   * Стоимость диагностики.
   */
  @Column(name = "price_medical_care")
  private Double priceMedicalCare;

  /**
   * Ctor.
   *
   * @param nameSubject наименование субъекта РФ
   * @param nomenclatureMc {@link NomenclatureMedicalCare}
   * @param priceMedicalCare стоимость диагностики
   */
  public DiagnosisPrice(final String nameSubject,
      final NomenclatureMedicalCare nomenclatureMc,
      final Double priceMedicalCare) {
    this.nameSubject = nameSubject;
    this.nomenclatureMc = nomenclatureMc;
    this.priceMedicalCare = priceMedicalCare;
  }
}
