package com.novmik.tpc.medicament;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Medicament {

  private String innMedicament;
  private String numberCodeScheme;
  private Float dose;
  private Float doseMin;
  private Float doseMax;
  private String unitOfMeasurement;
  private Integer numberDaysDrug;
  private BigDecimal requiredDose;
}
