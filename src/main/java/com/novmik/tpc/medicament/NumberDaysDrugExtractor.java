package com.novmik.tpc.medicament;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.novmik.tpc.medicament.NumberDaysDrugExtractorConstant.*;

@Slf4j
@AllArgsConstructor
@Component
public class NumberDaysDrugExtractor {

    private final CheckMatcherPatternScheme checkMatcherPatternScheme;

    Integer getNumberDaysDrug(String strNumberDaysDrugTreatments, Integer numberDaysDrugTreatments) {
        if (strNumberDaysDrugTreatments.contains(EVERY_DAY)) {
            return numberDaysDrugTreatments;
        }
        if (strNumberDaysDrugTreatments.contains(FIRST_DAY)) {
            return 1;
        }
        if (checkMatcherPatternScheme.checkOnceEveryNxDays(strNumberDaysDrugTreatments)) {
            int lengthStart = strNumberDaysDrugTreatments.indexOf(" 1 раз в ") + " 1 раз в ".length();
            int lengthEnd = strNumberDaysDrugTreatments.indexOf(" дней");
            String substring = strNumberDaysDrugTreatments.substring(lengthStart, lengthEnd).trim();
            int i = Integer.parseInt(substring) / numberDaysDrugTreatments;
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" во 2-й день")) {
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 1 раз в 21 день")) {
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" в 8-й день")) {
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains("в 1-5-й дни, 8-12-й дни, 15-19-й дни, 22-26-й дни")) {
            return 20;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 3-й, 5-й дни")) {
            return 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-5-й дни")) {
            return 5;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-3-й дни")) {
            return 3;
        }

        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й, 15-й, 22-й, 29-й, 36-й дни")) {
            return 6;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й, 15-й, 22-й дни")) {
            return 4;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 3-й, 5-й, 7-й дни")) {
            return 4;
        }
        if (strNumberDaysDrugTreatments.contains(" во 2-й, 4-й, 6-й, 8-й дни")) {
            return 4;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й, 15-й дни")) {
            return 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 15-й, 22-й дни")) {
            return 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 15-й, 29-й дни")) {
            return 3;
        }
        if (strNumberDaysDrugTreatments.contains(" во 2-й, 15-й, 22-й дни")) {
            return 3;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-10-й дни")) {
            return 10;
        }
        if (strNumberDaysDrugTreatments.contains(" в 8-17-й дни")) {
            return 14;
        }
        if (strNumberDaysDrugTreatments.contains(" в 8-21-й дни")) {
            return 14;
        }
        if (strNumberDaysDrugTreatments.contains(" в 5-16-й дни")) {
            return 12;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-21-й дни")) {
            return 21;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 8-й дни")) {
            return 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-й, 15-й дни")) {
            return 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-4-й дни")) {
            return 4;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-7-й дни")) {
            return 7;
        }
        if (strNumberDaysDrugTreatments.contains(" 46-часовая инфузия в 1-2-й дни")) {
//TODO: ПРОВЕРИТЬ как считается кол-во дней когда написано 3200 доза ( 1600 в день)...
            return 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-2-й дни")) {
            return 2;
        }
        if (strNumberDaysDrugTreatments.contains(" в 6-15-й дни")) {
            return 10;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-14-й дни каждые 3 недели")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" в 1-14-й дни")) {
            return 14;
        }
        if (strNumberDaysDrugTreatments.contains(" 3 раза в неделю")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 1 раз в 2 недели")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 2 раза в неделю")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" 1 раз в 3 недели")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        if (strNumberDaysDrugTreatments.contains(" внутрипузырно, первая инстилляция в день выполнения трансуретральной резекции (тур), далее 1 раз в неделю")) {
//TODO: Изменить, Перепроверить как считается количество дней приёма лекарства
            return 1;
        }
        log.error("Количество дней введения лекарственных препаратов, оплачиваемых по КСГ, не определено.");
        return 0;
    }
}
