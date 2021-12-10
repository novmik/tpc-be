package com.novmik.tpc.cdt;

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
 * Случай КСЛП entity class.
 * (Коэффициент сложности лечения пациентов)
 * Case Coefficient Of Difficulty In Treating Patients
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "case_cdt")
public class CaseCdt {

  /**
   * id Случай СЛПС.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer idCaseCdt;
  /**
   * Случай с КСЛП.
   */
  @Column(name = "nomination_case_cdt")
  private String nominationCaseCdt;

  /**
   * Ctor.
   *
   * @param nominationCaseCdt наименование случая КСЛП
   */
  public CaseCdt(final String nominationCaseCdt) {
    this.nominationCaseCdt = nominationCaseCdt;
  }
}
