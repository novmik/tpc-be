package com.novmik.tpc.subject;

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
 * Субъект РФ entity class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "subject_of_rf")
public class Subject {

  /**
   * id субъекта РФ.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idSubject;
  /**
   * Наименование субъекта РФ.
   */
  @Column(name = "name_subject")
  private String nameSubject;
  /**
   * БС КС.
   * Базовая ставка круглосуточного стационара
   * Base Rate Round The Clock Care Facility
   */
  @Column(name = "baserate_st")
  private Double baseRateSt;
  /**
   * БС ДС.
   * Базовая ставка дневного стационара
   * Base Rate Day Care Facility
   */
  @Column(name = "baserate_ds")
  private Double baseRateDs;

  /**
   * Ctor.
   *
   * @param nameSubject Наименование субъекта РФ
   * @param baseRateSt  БС КС
   * @param baseRateDs  БС ДС
   */
  public Subject(final String nameSubject,
      final Double baseRateSt,
      final Double baseRateDs) {
    this.nameSubject = nameSubject;
    this.baseRateSt = baseRateSt;
    this.baseRateDs = baseRateDs;
  }
}