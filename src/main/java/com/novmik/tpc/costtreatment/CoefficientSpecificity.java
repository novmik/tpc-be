package com.novmik.tpc.costtreatment;

/*
Медицинские организации, функции и полномочия учредителей в отношении которых осуществляет Правительство РФ или федеральные органы исполнительной власти.
Критерии классификации МО, влияющие на тариф:
- медицинские организации, подведомственные Управлению делами Президента РФ (typeMedicalInstitution = 1)
- федеральные медицинские организации на территории закрытого административно-территориального образования (typeMedicalInstitution = 2)
- иные медицинских организации, оказывающие указанную специализированную медицинскую помощь; при оказании медицинской реабилитации (typeMedicalInstitution = 3)
- образовательные организации высшего образования, осуществляющие оказание медицинской помощи (typeMedicalInstitution = 4)
- иные федеральные медицинские организации (typeMedicalInstitution = 5)

а) 1,4 - при значении коэффициента относительной затратоемкости, равном 2 и более, а для медицинских организаций,
подведомственных Управлению делами Президента Российской Федерации,- при значении коэффициента относительной затратоемкости, равном 1,7 и более;
б) 1,2 - при значении коэффициента относительной затратоемкости менее 2 и расположении
федеральной медицинской организации на территории закрытого административно-территориального образования;
в) 1 - при значении коэффициента относительной затратоемкости менее 2 и отсутствии на территории
муниципального образования иных медицинских организаций, оказывающих указанную специализированную медицинскую помощь, при оказании медицинской реабилитации,
а для образовательных организаций высшего образования, осуществляющих оказание медицинской помощи, медицинских организаций,
подведомственных Управлению делами Президента Российской Федерации, - при значении коэффициента относительной затратоемкости менее 1,7;
г) 0,8 - для случаев, не указанных в подпунктах "а" - "в" настоящего пункта.
 */

import lombok.extern.slf4j.Slf4j;

import static com.novmik.tpc.costtreatment.CostOfCompletedCaseOfTreatmentConstant.COEFFICIENT_SPECIFICITY_NOT_CORRECT;

@Slf4j
public abstract class CoefficientSpecificity {

    public static float calculate(final float rateRelativeIntensity, final Integer typeMedicalInstitution) {
        float coefficientSpecificity = 0;
        if (typeMedicalInstitution == 1) {
            coefficientSpecificity = rateRelativeIntensity >= 1.7 ? 1.4F : 1F;
        }
        if (typeMedicalInstitution == 2) {
            coefficientSpecificity = rateRelativeIntensity < 2 ? 1.2F : 1.4F;
        }
        if (typeMedicalInstitution == 3) {
            coefficientSpecificity = rateRelativeIntensity < 2 ? 1.0F : 1.4F;
        }
        if (typeMedicalInstitution == 4) {
            coefficientSpecificity = rateRelativeIntensity < 1.7 ? 1.0F : 1.4F;
        }
        if (typeMedicalInstitution == 5) {
            coefficientSpecificity = rateRelativeIntensity >= 2 ? 1.4F : 0.8F;
        }
        if (coefficientSpecificity == 0) {
            log.error(COEFFICIENT_SPECIFICITY_NOT_CORRECT, rateRelativeIntensity, typeMedicalInstitution);
        }
        return coefficientSpecificity;
    }

}
