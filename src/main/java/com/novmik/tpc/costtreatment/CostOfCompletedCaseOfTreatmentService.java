package com.novmik.tpc.costtreatment;

import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import com.novmik.tpc.drg.DiagnosisRelatedGroupsService;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.medicalinstitution.MedicalInstitution;
import com.novmik.tpc.medicalinstitution.MedicalInstitutionService;
import com.novmik.tpc.subject.SubjectOfRF;
import com.novmik.tpc.subject.SubjectOfRFService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.novmik.tpc.cdt.CdtConstant.DAY_CARE_FACILITY;
import static com.novmik.tpc.cdt.CdtConstant.ROUND_THE_CLOCK_CARE_FACILITY;
import static com.novmik.tpc.costtreatment.CostOfCompletedCaseOfTreatmentConstant.*;

/*
ССксг/кпг = БС х КЗксг/кпг х ((1 - Дзп) + Дзп х ПК х КД)
БС - размер средней стоимости законченного случая лечения без учета коэффициента дифференциации (базовая ставка), рублей;
КЗксг/кпг - коэффициент относительной затратоемкости по КСГ или КПГ, к которой отнесен данный случай госпитализации;
Дзп - доля заработной платы и прочих расходов в структуре стоимости КСГ (установленное Приложением 3 к Программе значение, к которому применяется  КД);
ПК - поправочный коэффициент оплаты КСГ или КПГ (интегрированный коэффициент, рассчитываемый на региональном уровне);
КД - коэффициент дифференциации, рассчитанный в соответствии с Постановлением № 462.

Поправочный коэффициент оплаты КСГ или КПГ для конкретного случая рассчитывается с учетом коэффициентов оплаты, установленных в субъекте Российской Федерации, по следующей формуле:
ПК = КСксг/кпг х КУСмо х КСЛП
КСксг/кпг - коэффициент специфики КСГ или КПГ, к которой отнесен данный случай госпитализации (используется в расчетах, в случае если указанный коэффициент определен в субъекте Российской Федерации для данной КСГ или КПГ);
КУСмо - коэффициент уровня медицинской организации, в которой был пролечен пациент;
КСЛП - коэффициент сложности лечения пациента (используется в расчетах, в случае если указанный коэффициент определен в субъекте Российской Федерации для данного случая).
ССксг/кпг = БС х КЗксг/кпг х ((1 - Дзп) + Дзп х КСксг/кпг х КУСмо х КСЛП х КД)
        baseRate - БС
        coefficientOfLevel_MO - КУС
        rateRelativeIntensity - КЗ
        wageShare - Дзп
        coefficientSpecificity = 1.0F - КС
        valueCDTP - КСЛП - расчитывается на frontend'e
        differentiationCoefficient - КД
Тариф на оплату медицинской помощи, оказываемой федеральной медицинской организацией в условиях при противоопухолевой лекарственной терапии злокачественных новообразований:
T = НФЗ х КБС х КЗксг/кпг х ((1 - Дзп) + Дзп х КД х КС х КСЛП) - для медицинской организации,функции и полномочия учредителей в отношении которых осуществляет Правительство Российской Федерации или федеральные органы исполнительной власти
НФЗ - средний норматив финансовых затрат на единицу объема предоставления медицинской помощи в z-х условиях, оказываемой федеральными медицинскими организациями, установленный Программой;
КБС - коэффициент приведения среднего норматива финансовых затрат на единицу объема предоставления медицинской помощи в z-х условиях к базовой ставке, исключающей влияние применяемых коэффициентов относительной затратоемкости и специфики оказания медицинской помощи, коэффициента дифференциации и коэффициента сложности лечения пациентов, принимающий значение 0,41 - для стационара и 0,52 - для дневного стационара;
        financialCostStandard - НФЗ
        coefficientBaseRate - КБС
*/

@Slf4j
@AllArgsConstructor
@Service
public class CostOfCompletedCaseOfTreatmentService {

    private final MedicalInstitutionService miService;
    private final DiagnosisRelatedGroupsService drgService;
    private final SubjectOfRFService subjectOfRFService;

    protected CostTreatmentResponse getCostTreatmentWithDrg(final CostTreatmentRequest costTreatmentRequest) {
        if (ObjectUtils.anyNull(
                costTreatmentRequest,
                costTreatmentRequest.getIdMedicalInstitution(),
                costTreatmentRequest.getNumberDrg(),
                costTreatmentRequest.getValueCdt()
        )
                || costTreatmentRequest.getIdMedicalInstitution() <= 0
                || StringUtils.isBlank(costTreatmentRequest.getNumberDrg())
        ) {
            throw new BadRequestException(SUBJECT_ID_NUMBER_DRG_VALUE_CDT + costTreatmentRequest);
        }
        if (costTreatmentRequest.getValueCdt() <= 0) {
            costTreatmentRequest.setValueCdt(DEFAULT_VALUE_CDT);
        }
        MedicalInstitution medicalInstitutionById = miService.getMedicalInstitutionById(costTreatmentRequest.getIdMedicalInstitution()).orElseThrow();
        SubjectOfRF byNameSubject = subjectOfRFService.findByNameSubject(medicalInstitutionById.getNameSubject()).orElseThrow();
        DiagnosisRelatedGroups diagnosisRelatedGroups = drgService.byNumberDrg(costTreatmentRequest.getNumberDrg()).orElseThrow();
        BigDecimal costTreatment;
        if (medicalInstitutionById.getTypeMedicalInstitution() != 0) {
            costTreatment = getCostTreatmentFederal(
                    costTreatmentRequest.getValueCdt(),
                    medicalInstitutionById,
                    diagnosisRelatedGroups
            );
        } else {
            costTreatment = getCostTreatmentNotFederal(
                    costTreatmentRequest.getValueCdt(),
                    medicalInstitutionById,
                    byNameSubject,
                    diagnosisRelatedGroups
            );
        }
        return new CostTreatmentResponse(costTreatment, diagnosisRelatedGroups);
    }

    private BigDecimal getCostTreatmentNotFederal(
            final Float valueCdt,
            final MedicalInstitution medicalInstitutionById,
            final SubjectOfRF byNameSubject,
            final DiagnosisRelatedGroups diagnosisRelatedGroups) {
        String numberDrg = diagnosisRelatedGroups.getNumberDrg();
        float wageShare = diagnosisRelatedGroups.getWageShare();
        double baseRate;
        float coefficientOfLevel_mo;
        if (numberDrg.toLowerCase().startsWith(ROUND_THE_CLOCK_CARE_FACILITY.toLowerCase())) {
            baseRate = byNameSubject.getBaseRateRoundTheClockCareFacility();
            coefficientOfLevel_mo = medicalInstitutionById.getCoefficientOfLevel_MO_rtccf();
        } else if (numberDrg.toLowerCase().startsWith(DAY_CARE_FACILITY.toLowerCase())) {
            baseRate = byNameSubject.getBaseRateDayCareFacility();
            coefficientOfLevel_mo = medicalInstitutionById.getCoefficientOfLevel_MO_dcf();
        } else {
            throw new BadRequestException(NUMBER_DRG_INCORRECT + numberDrg);
        }
        if (ObjectUtils.anyNull(baseRate, coefficientOfLevel_mo)) {
            throw new BadRequestException(VALUES_IN_TABLE_IS_NOT_EXISTS + baseRate + "/" + coefficientOfLevel_mo);
        }
        BigDecimal bigDecimalWageShare = BigDecimal.valueOf(wageShare).setScale(4, RoundingMode.HALF_UP);
        float rateRelativeIntensity = diagnosisRelatedGroups.getRateRelativeIntensity();
        float differentiationCoefficient = medicalInstitutionById.getDifferentiationCoefficient();
        return bigDecimalWageShare
                .multiply(BigDecimal.valueOf(COEFFICIENT_SPECIFICITY_NOT_FEDERAL))
                .multiply(BigDecimal.valueOf(coefficientOfLevel_mo).setScale(2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(valueCdt))
                .multiply(BigDecimal.valueOf(differentiationCoefficient))
                .add(BigDecimal.ONE)
                .subtract(bigDecimalWageShare)
                .multiply(BigDecimal.valueOf(baseRate).setScale(2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(rateRelativeIntensity).setScale(4, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getCostTreatmentFederal(
            final Float valueCdt,
            final MedicalInstitution medicalInstitutionById,
            final DiagnosisRelatedGroups diagnosisRelatedGroups) {
        float rateRelativeIntensity = diagnosisRelatedGroups.getRateRelativeIntensity();
        float wageShare = diagnosisRelatedGroups.getWageShare();
        double financialCostStandard;
        double coefficientBaseRate;
        String numberDrg = diagnosisRelatedGroups.getNumberDrg();
        if (numberDrg.toLowerCase().startsWith(ROUND_THE_CLOCK_CARE_FACILITY.toLowerCase())) {
            financialCostStandard = FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY;
            coefficientBaseRate = COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY;
        } else if (numberDrg.toLowerCase().startsWith(DAY_CARE_FACILITY.toLowerCase())) {
            financialCostStandard = FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY;
            coefficientBaseRate = COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY;
        } else {
            throw new BadRequestException(NUMBER_DRG_INCORRECT + numberDrg);
        }
        BigDecimal bigDecimalWageShare = BigDecimal.valueOf(wageShare).setScale(4, RoundingMode.HALF_UP);
        float differentiationCoefficient = medicalInstitutionById.getDifferentiationCoefficient();
        float coefficientSpecificity = CoefficientSpecificity.calculate(
                rateRelativeIntensity,
                medicalInstitutionById.getTypeMedicalInstitution());
        if (coefficientSpecificity == 0) {
            throw new BadRequestException(COEFFICIENT_SPECIFICITY_IS_ZERO);
        }
        return bigDecimalWageShare
                .multiply(BigDecimal.valueOf(coefficientSpecificity))
                .multiply(BigDecimal.valueOf(valueCdt))
                .multiply(BigDecimal.valueOf(differentiationCoefficient))
                .add(BigDecimal.ONE)
                .subtract(bigDecimalWageShare)
                .multiply(BigDecimal.valueOf(financialCostStandard).setScale(2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(coefficientBaseRate).setScale(2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(rateRelativeIntensity).setScale(4, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }
}