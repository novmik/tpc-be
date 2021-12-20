package com.novmik.tpc.medicament;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SeparationStrOfSchemeUtilsTest {

  @Test
  void canGetMedicamentsWithPlus() {
    String innMedicaments = "Inn1 + Inn2 + Inn3 + Inn1";
    assertThat(SeparationStrOfSchemeUtils.getMedicaments(innMedicaments)).isNotNull();
    assertThat(SeparationStrOfSchemeUtils.getMedicaments(innMedicaments).size()).isEqualTo(4);
  }

  @Test
  void canGetMedicamentsWithoutPlus() {
    String innMedicaments = "Inn1";
    assertThat(SeparationStrOfSchemeUtils.getMedicaments(innMedicaments).get(0))
        .isEqualTo(innMedicaments);
    assertThat(SeparationStrOfSchemeUtils.getMedicaments(innMedicaments).size()).isEqualTo(1);
  }

  @Test
  void canGetMedicamentAndSpecificationFromPreparedDescriptionSchemeWithPlus() {
    String descriptionScheme = "Inn1 25 мг/м² в 1-й, 8-й дни + Inn2 6 мг/кг в 1-й день";
    assertThat(SeparationStrOfSchemeUtils
        .getMedicamentAndSpecificationFromPreparedDescriptionScheme(descriptionScheme)).isNotNull();
    assertThat(SeparationStrOfSchemeUtils
        .getMedicamentAndSpecificationFromPreparedDescriptionScheme(descriptionScheme)
        .size()).isEqualTo(2);
  }

  @Test
  void canGetMedicamentAndSpecificationFromPreparedDescriptionSchemeWithoutPlus() {
    String descriptionScheme = "Inn1 25 мг/м² в 1-й, 8-й дни";
    assertThat(SeparationStrOfSchemeUtils
        .getMedicamentAndSpecificationFromPreparedDescriptionScheme(descriptionScheme).get(0))
        .isEqualTo(descriptionScheme);
    assertThat(SeparationStrOfSchemeUtils
        .getMedicamentAndSpecificationFromPreparedDescriptionScheme(descriptionScheme)
        .size()).isEqualTo(1);
  }
}