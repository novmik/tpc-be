package com.novmik.tpc.medicament;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Medicament {

    private String innMedicament;
    private String numberCodeScheme;
    private Float dose;
    private Float dose_min;
    private Float dose_max;
    private String unitOfMeasurement;
    private Integer numberDaysDrug;
    private BigDecimal requiredDose;
}
