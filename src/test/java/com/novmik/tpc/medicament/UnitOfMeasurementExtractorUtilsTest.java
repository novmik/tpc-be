package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UnitOfMeasurementExtractorUtilsTest {

    @Test
    void getUnit() {
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("абиратерон 1000 мг ежедневно")).isEqualTo("мг");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("этопозид 100 мг/м² в 1-5-й дни")).isEqualTo("мг/м²");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("бевацизумаб 7,5-15 мг/кг в 1-й день")).isEqualTo("мг/кг");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("карбоплатин auc 5 в 1-й день")).isEqualTo("auc");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("интерферон альфа-2b 20 млн ме/м² в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни")).isEqualTo("млн ме/м²");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("интерферон альфа 6-9 млн ме 3 раза в неделю")).isEqualTo("млн ме");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("филграстим 5 мкг/кг в 6-15-й дни")).isEqualTo("мкг/кг");
        assertThat(UnitOfMeasurementExtractorUtils.getUnit("test")).isEqualTo("");
    }
}