package com.novmik.tpc.medicament;

import static com.novmik.tpc.medicament.MedicamentConstants.EVERY_DAY;
import static com.novmik.tpc.medicament.MedicamentConstants.FIRST_DAY;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Извленеие кол-ва дней ЛТ.
 */
@Slf4j
@Component
public final class NumberDaysDrugExtractorUtils {

  /**
   * Получить кол-во дней ЛТ.
   *
   * @param strNumberDaysDrug кол-во дней ЛТ
   *                          медиц. препаратом
   * @param numberDays        кол-во дней СЛТ
   * @return кол-во дней ЛТ
   */
  public static Integer getNumberDaysDrug(
      final String strNumberDaysDrug,
      final Integer numberDays) {
    Integer numberDaysDrug = 0;
    if (strNumberDaysDrug.contains(EVERY_DAY)) {
      numberDaysDrug = numberDays;
    }
    if (strNumberDaysDrug.contains(FIRST_DAY)) {
      numberDaysDrug = 1;
    }
    if (CheckMatcherPatternSchemeHelper.checkOnceEveryNxDays(strNumberDaysDrug)) {
      final int lengthStart = strNumberDaysDrug.indexOf(" 1 раз в ") + " 1 раз в ".length();
      final int lengthEnd = strNumberDaysDrug.indexOf(" дней");
      final String substring = strNumberDaysDrug.substring(lengthStart, lengthEnd).trim();
      final int days = Integer.parseInt(substring) / numberDays;
      log.info("Кол-во дней: {}", days);
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" во 2-й день")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" 1 раз в 21 день")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" в 8-й день")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains("в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни")) {
      numberDaysDrug = 20;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 3-й, 5-й дни")) {
      numberDaysDrug = 3;
    }
    if (strNumberDaysDrug.contains(" в 1-5-й дни")) {
      numberDaysDrug = 5;
    }
    if (strNumberDaysDrug.contains(" в 1-3-й дни")) {
      numberDaysDrug = 3;
    }

    if (strNumberDaysDrug.contains(" в 1-й, 8-й, 15-й, 22-й, 29-й, 36-й дни")) {
      numberDaysDrug = 6;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 8-й, 15-й, 22-й дни")) {
      numberDaysDrug = 4;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 3-й, 5-й, 7-й дни")) {
      numberDaysDrug = 4;
    }
    if (strNumberDaysDrug.contains(" во 2-й, 4-й, 6-й, 8-й дни")) {
      numberDaysDrug = 4;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 8-й, 15-й дни")) {
      numberDaysDrug = 3;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 15-й, 22-й дни")) {
      numberDaysDrug = 3;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 15-й, 29-й дни")) {
      numberDaysDrug = 3;
    }
    if (strNumberDaysDrug.contains(" во 2-й, 15-й, 22-й дни")) {
      numberDaysDrug = 3;
    }
    if (strNumberDaysDrug.contains(" в 1-10-й дни")) {
      numberDaysDrug = 10;
    }
    if (strNumberDaysDrug.contains(" в 8-17-й дни")) {
      numberDaysDrug = 14;
    }
    if (strNumberDaysDrug.contains(" в 8-21-й дни")) {
      numberDaysDrug = 14;
    }
    if (strNumberDaysDrug.contains(" в 5-16-й дни")) {
      numberDaysDrug = 12;
    }
    if (strNumberDaysDrug.contains(" в 1-21-й дни")) {
      numberDaysDrug = 21;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 8-й дни")) {
      numberDaysDrug = 2;
    }
    if (strNumberDaysDrug.contains(" в 1-й, 15-й дни")) {
      numberDaysDrug = 2;
    }
    if (strNumberDaysDrug.contains(" в 1-4-й дни")) {
      numberDaysDrug = 4;
    }
    if (strNumberDaysDrug.contains(" в 1-7-й дни")) {
      numberDaysDrug = 7;
    }
    if (strNumberDaysDrug.contains(" 46-часовая инфузия в 1-2-й дни")) {
      numberDaysDrug = 2;
    }
    if (strNumberDaysDrug.contains(" в 1-2-й дни")) {
      numberDaysDrug = 2;
    }
    if (strNumberDaysDrug.contains(" в 6-15-й дни")) {
      numberDaysDrug = 10;
    }
    if (strNumberDaysDrug.contains(" в 1-14-й дни каждые 3 недели")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" в 1-14-й дни")) {
      numberDaysDrug = 14;
    }
    if (strNumberDaysDrug.contains(" 3 раза в неделю")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" 1 раз в 2 недели")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" 2 раза в неделю")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(" 1 раз в 3 недели")) {
      numberDaysDrug = 1;
    }
    if (strNumberDaysDrug.contains(
        " внутрипузырно, первая инстилляция в день"
            + " выполнения трансуретральной резекции (тур), далее 1 раз в неделю")) {
      numberDaysDrug = 1;
    }
    if (numberDaysDrug == 0) {
      log.error(
          "Количество дней введения лекарственных препаратов, оплачиваемых по КСГ, не определено.");
    }
    return numberDaysDrug;
  }

  private NumberDaysDrugExtractorUtils() {
  }
}
