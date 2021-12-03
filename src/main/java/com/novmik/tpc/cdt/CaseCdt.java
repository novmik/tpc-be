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

/*
Таблица: Случай сложности лечения пациентов
стационара (КСЛП) / CaseCoefficientOfDifficultyInTreatingPatients
Колонки: Случаи, для которых установлен КСЛП
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "case_cdt")
public class CaseCdt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "nomination_case_cdt")
  private String nominationCaseCdt;

  public CaseCdt(final String nominationCaseCdt) {
    this.nominationCaseCdt = nominationCaseCdt;
  }
}
