package com.novmik.tpc.medicament;

import com.novmik.tpc.schemepharmacotherapy.SchemePharmacotherapy;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 * Удаление(очищение) не нужного
 * информации из строки описания СЛТ.
 */
@Component
@SuppressWarnings("PMD.LawOfDemeter")
public final class DescriptionSchemeTransformationUtils {

  /**
   * Удаление не нужного.
   *
   * @param scheme {@link SchemePharmacotherapy}
   * @param medicaments список МНН ЛП
   * @return подготовленнное описание СЛТ
   */
  public static String removeExcess(
      final SchemePharmacotherapy scheme,
      final List<String> medicaments) {
    return removeCycleInEndStr(
        removeStartUnnecessary(
            removeIntravenouslyAndNbps(
                scheme.getDescription().toLowerCase(Locale.ROOT)),
            medicaments));
  }

  /**
   * Изменение запятой на точку
   * в строке с цифрами.
   *
   * @param strWithCommas строка с запятой
   * @return строка с точкой
   */
  public static String replaceCommasOnPointsInNumbers(final String strWithCommas) {
    String str;
    if (Pattern.compile("\\d+,\\d+").matcher(strWithCommas).find()) {
      str = strWithCommas.replaceAll(",", ".");
    } else {
      str = strWithCommas;
    }
    return str;
  }

  private static String removeStartUnnecessary(final String description,
      final List<String> medicamentsList) {
    final String medicament = medicamentsList.get(0);
    final int startIndex = description.indexOf(medicament);
    return startIndex > 0 ? description.substring(
        startIndex) : description;
  }

  private static String removeCycleInEndStr(final String description) {
    return description.replaceAll("; цикл.*$", "");
  }

  private static String removeIntravenouslyAndNbps(final String description) {
    return description
        .replaceAll(" в/в ", " ")
        .replaceAll(" ", " ");
  }

  private DescriptionSchemeTransformationUtils() {
  }
}
