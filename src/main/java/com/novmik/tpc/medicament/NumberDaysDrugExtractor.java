package com.novmik.tpc.medicament;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.novmik.tpc.medicament.NumberDaysDrugExtractorConstant.*;

@Slf4j
@AllArgsConstructor
@Component
public class NumberDaysDrugExtractor {

    protected static Integer getNumberDaysDrug(final String strNumberDaysDrugTreatments, final Integer numberDaysDrugTreatments) {
        Integer numberDaysDrug = 0;
        if (strNumberDaysDrugTreatments.contains(EVERY_DAY)) {
            numberDaysDrug = numberDaysDrugTreatments;
        }
        if (strNumberDaysDrugTreatments.contains(FIRST_DAY)) {
            numberDaysDrug = 1;
        }
        if (CheckMatcherPatternScheme.checkOnceEveryNxDays(strNumberDaysDrugTreatments)) {
            int lengthStart = strNumberDaysDrugTreatments.indexOf(" 1 раз в ") + " 1 раз в ".length();
            int lengthEnd = strNumberDaysDrugTreatments.indexOf(" дней");
            String substring = strNumberDaysDrugTreatments.substring(lengthStart, lengthEnd).trim();
            int i = Integer.parseInt(substring) / numberDaysDrugTreatments;
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" во 2-й день")) {
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 1 раз в 21 день")) {
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" в 8-й день")) {
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains("в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни")) {
            numberDaysDrug = 20;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 3-й, 5-й дни")) {
            numberDaysDrug = 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-5-й дни")) {
            numberDaysDrug = 5;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-3-й дни")) {
            numberDaysDrug = 3;
        }

        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й, 15-й, 22-й, 29-й, 36-й дни")) {
            numberDaysDrug = 6;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й, 15-й, 22-й дни")) {
            numberDaysDrug = 4;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 3-й, 5-й, 7-й дни")) {
            numberDaysDrug = 4;
        }
        if (strNumberDaysDrugTreatments.contains(" во 2-й, 4-й, 6-й, 8-й дни")) {
            numberDaysDrug = 4;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й, 15-й дни")) {
            numberDaysDrug = 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 15-й, 22-й дни")) {
            numberDaysDrug = 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 15-й, 29-й дни")) {
            numberDaysDrug = 3;
        }
        if (strNumberDaysDrugTreatments.contains(" во 2-й, 15-й, 22-й дни")) {
            numberDaysDrug = 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-10-й дни")) {
            numberDaysDrug = 10;
        }
        if (strNumberDaysDrugTreatments.contains(" в 8-17-й дни")) {
            numberDaysDrug = 14;
        }
        if (strNumberDaysDrugTreatments.contains(" в 8-21-й дни")) {
            numberDaysDrug = 14;
        }
        if (strNumberDaysDrugTreatments.contains(" в 5-16-й дни")) {
            numberDaysDrug = 12;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-21-й дни")) {
            numberDaysDrug = 21;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й дни")) {
            numberDaysDrug = 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 15-й дни")) {
            numberDaysDrug = 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-4-й дни")) {
            numberDaysDrug = 4;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-7-й дни")) {
            numberDaysDrug = 7;
        }
        if (strNumberDaysDrugTreatments.contains(" 46-часовая инфузия в 1-2-й дни")) {
//TODO: ПРОВЕРИТЬ как считается кол-во дней когда написано 3200 доза ( 1600 в день)...
            numberDaysDrug = 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-2-й дни")) {
            numberDaysDrug = 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 6-15-й дни")) {
            numberDaysDrug = 10;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-14-й дни каждые 3 недели")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-14-й дни")) {
            numberDaysDrug = 14;
        }
        if (strNumberDaysDrugTreatments.contains(" 3 раза в неделю")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 1 раз в 2 недели")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 2 раза в неделю")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 1 раз в 3 недели")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (strNumberDaysDrugTreatments.contains(" внутрипузырно, первая инстилляция в день выполнения трансуретральной резекции (тур), далее 1 раз в неделю")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            numberDaysDrug = 1;
        }
        if (numberDaysDrug == 0) {
            log.error("Количество дней введения лекарственных препаратов, оплачиваемых по КСГ, не определено.");
        }
        return numberDaysDrug;
    }
}
