package com.novmik.tpc.medicament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Извлечение данных из СЛТ.
 */
@Component
public final class SeparationStrOfSchemeUtils {

  /**
   * Извлечение мед. препаратов из строки.
   *
   * @param innMedicaments строка с МНН
   * @return список МНН
   */
  public static List<String> getMedicaments(final String innMedicaments) {
    List<String> separationMeds;
    if (innMedicaments.contains(" + ")) {
      separationMeds = Arrays.asList(innMedicaments.split("(\\s*\\+\\s*)"));
    } else {
      separationMeds = List.of(innMedicaments);
    }
    return separationMeds;
  }

  /**
   * Разделение на МНН и описание применения ЛП.
   *
   * @param descriptionScheme строка описания схемы
   * @return список МНН с описанием применения ЛП
   */
  public static List<String> getMedicamentAndSpecificationFromPreparedDescriptionScheme(
      final String descriptionScheme) {
    List<String> medsDescription;
    if (descriptionScheme.contains(" + ")) {
      medsDescription = Arrays.asList(
          descriptionScheme.split("(\\s*\\+\\s*)"));
    } else {
      medsDescription = new ArrayList<>();
      medsDescription.add(descriptionScheme);
    }
    return medsDescription;
  }

  private SeparationStrOfSchemeUtils() {
  }
}
