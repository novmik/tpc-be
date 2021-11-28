package com.novmik.tpc.typemedicalinstitution;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
Таблица: Тип Медицинскай организации (МО)
Колонки: Тип МО, Описание
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "type_medical_institution")
public class TypeMedicalInstitution {

    @Id
    @Column(name = "type_mi")
    private Integer type_mi;
    @Column(name = "description")
    private String description;
}
