package com.novmik.tpc.typemedicalinstitution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Тип МО entity class.
 * Тип Медицинскай организации entity class
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "type_medical_institution")
public class TypeMedicalInstitution {

  /**
   * Тип МО.
   */
  @Id
  @Column(name = "type_mi")
  private Integer typeMi;
  /**
   * Описание типа МО.
   */
  @Column(name = "description")
  private String description;
}
