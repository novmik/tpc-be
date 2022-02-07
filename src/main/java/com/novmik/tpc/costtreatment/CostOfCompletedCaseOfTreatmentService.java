package com.novmik.tpc.costtreatment;

import com.novmik.tpc.cdt.CoefficientDifficultyTreatingService;
import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import com.novmik.tpc.drg.DiagnosisRelatedGroupsService;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.medicalinstitution.MedicalInstitution;
import com.novmik.tpc.medicalinstitution.MedicalInstitutionService;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Стоимость одного случая госпитализации
 * по КСГ для случаев лекарственной
 * терапии взрослых со
 * злокачественными новообразованиями
 * business interface layer.
 *
 * Согласно методическим рекомендациям
 * по способам оплаты медицинской
 * помощи за счет средств обязательного
 * медицинского страхования для 2021 года
 */
@Slf4j
@AllArgsConstructor
@Service
@SuppressWarnings({"PMD.CommentSize", "PMD.LawOfDemeter"})
public class CostOfCompletedCaseOfTreatmentService {

  /**
   * DEFAULT_VALUE_CDT.
   */
  public static final float DEFAULT_VALUE_CDT = 1F;
  /**
   * COEFFICIENT_SPECIFICITY_NOT_FEDERAL.
   */
  public static final float COEFFICIENT_SPECIFICITY_NOT_FEDERAL = 1F;
  /**
   * COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY.
   */
  public static final double COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY = 0.41;
  /**
   * COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY.
   */
  public static final double COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY = 0.52;
  /**
   * FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY.
   */
  public static final double FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY = 56_680.90;
  /**
   * FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY.
   */
  public static final double FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY = 25_617.30;

  /**
   * {@link MedicalInstitutionService}.
   */
  private final MedicalInstitutionService miService;
  /**
   * {@link DiagnosisRelatedGroupsService}.
   */
  private final DiagnosisRelatedGroupsService drgService;
  /**
   * {@link SubjectService}.
   */
  private final SubjectService subjectService;

  /**
   * Стоимость госпитализации для
   * случаев лекарственной терапии.
   *
   * @param costRequest {@link CostTreatmentRequest}
   * @return {@link CostTreatmentResponse}
   * @throws BadRequestException если некорректные данные
   */
  protected CostTreatmentResponse getCostTreatmentWithDrg(
      final CostTreatmentRequest costRequest) {
    if (ObjectUtils.anyNull(
        costRequest,
        costRequest.getIdMi(),
        costRequest.getNumberDrg(),
        costRequest.getValueCdt()
    )
        || costRequest.getIdMi() <= 0
        || StringUtils.isBlank(costRequest.getNumberDrg())
    ) {
      throw new BadRequestException(
          "Субъект id/Номер КСГ/Значение КСГ указаны неверно: " + costRequest);
    }
    if (costRequest.getValueCdt() <= 0) {
      costRequest.setValueCdt(DEFAULT_VALUE_CDT);
    }
    final MedicalInstitution miById = miService.getMedicalInstitutionById(
        costRequest.getIdMi());
    final DiagnosisRelatedGroups drg = drgService.byNumberDrg(
        costRequest.getNumberDrg()).orElseThrow();
    BigDecimal costTreatment;
    if (miById.getTypeMi() == 0) {
      final Subject byNameSubject = subjectService.findByNameSubject(
          miById.getNameSubject()).orElseThrow();
      costTreatment = getCostTreatmentNotFederal(
          costRequest.getValueCdt(),
          miById,
          byNameSubject,
          drg
      );
    } else {
      costTreatment = getCostTreatmentFederal(
          costRequest.getValueCdt(),
          miById,
          drg
      );

    }
    return new CostTreatmentResponse(costTreatment, drg);
  }

  /**
   * Стоимость госпитализации для
   * случаев лекарственной
   * терапии не федеральных МО.
   *
   * <p>СС = БС*КЗ*((1 - Дзп) + Дзп*ПК*КД)
   * БС - размер средней стоимости законченного
   * случая лечения без учета коэффициента
   * дифференциации (базовая ставка), рублей;
   * КЗксг/кпг - коэффициент относительной
   * затратоемкости по КСГ или КПГ, к которой
   * отнесен данный случай госпитализации;
   * Дзп - доля заработной платы и прочих
   * расходов в структуре стоимости
   * КСГ (установленное Приложением 3 к
   * Программе значение, к которому
   * применяется  КД);
   * ПК - поправочный коэффициент оплаты
   * КСГ или КПГ (интегрированный коэффициент,
   * рассчитываемый на региональном уровне);
   * КД - коэффициент дифференциации,
   * рассчитанный в соответствии с
   * Постановлением № 462.
   *
   * <p>Поправочный коэффициент оплаты КСГ
   * или КПГ для конкретного случая
   * рассчитывается с учетом
   * коэффициентов оплаты, установленных в
   * субъекте Российской Федерации, по
   * следующей формуле:
   *
   * <p>ПК = КСксг/кпг х КУСмо х КСЛП
   * КСксг/кпг - коэффициент специфики
   * КСГ или КПГ, к которой отнесен данный
   * случай госпитализации (используется
   * в расчетах, в случае если указанный
   * коэффициент определен в субъекте
   * Российской Федерации для данной
   * КСГ или КПГ) = 1F;
   * КУСмо - коэффициент уровня медицинской
   * организации, в которой был
   * пролечен пациент;
   * КСЛП - коэффициент сложности лечения
   * пациента (используется в расчетах, в случае
   * если указанный коэффициент определен в
   * субъекте Российской Федерации для
   * данного случая).
   *
   * <p>СС = БС*КЗ*((1-Дзп)+Дзп*КС*КУСмо*КСЛП*КД)
   *
   * @param valueCdt      значение КСЛП
   * @param miById        {@link MedicalInstitution}
   * @param byNameSubject {@link Subject}
   * @param drg           {@link DiagnosisRelatedGroups}
   * @return стоимость ЛТ
   * @throws BadRequestException если некорректные данные
   */
  private BigDecimal getCostTreatmentNotFederal(
      final Float valueCdt,
      final MedicalInstitution miById,
      final Subject byNameSubject,
      final DiagnosisRelatedGroups drg) {
    final String numberDrg = drg.getNumberDrg();
    double baseRate;
    float coefficientLevel;
    if (numberDrg.toLowerCase(Locale.ROOT)
        .startsWith(CoefficientDifficultyTreatingService
            .ROUND_THE_CLOCK_CARE_FACILITY.toLowerCase(Locale.ROOT))) {
      baseRate = byNameSubject.getBaseRateSt();
      coefficientLevel = miById.getCoefficientSt();
    } else if (numberDrg
        .toLowerCase(Locale.ROOT)
        .startsWith(CoefficientDifficultyTreatingService
            .DAY_CARE_FACILITY.toLowerCase(Locale.ROOT))) {
      baseRate = byNameSubject.getBaseRateDs();
      coefficientLevel = miById.getCoefficientDs();
    } else {
      throw new BadRequestException(
          "Номер КСГ не верный: " + numberDrg);
    }
    if (ObjectUtils.anyNull(baseRate, coefficientLevel)) {
      throw new BadRequestException(String.format(
          "Значения БС/КУС в таблице отсутствуют: %s/%s", baseRate, coefficientLevel));
    }
    final float wageShare = drg.getWageShare();
    final BigDecimal bdWageShare = BigDecimal
        .valueOf(wageShare)
        .setScale(4, RoundingMode.HALF_UP);
    final float rateIntensity = drg.getRateIntensity();
    final float diffCoefficient = miById.getDiffCoefficient();
    return bdWageShare
        .multiply(BigDecimal.valueOf(COEFFICIENT_SPECIFICITY_NOT_FEDERAL))
        .multiply(BigDecimal.valueOf(coefficientLevel).setScale(2, RoundingMode.HALF_UP))
        .multiply(BigDecimal.valueOf(valueCdt))
        .multiply(BigDecimal.valueOf(diffCoefficient))
        .add(BigDecimal.ONE)
        .subtract(bdWageShare)
        .multiply(BigDecimal.valueOf(baseRate).setScale(2, RoundingMode.HALF_UP))
        .multiply(BigDecimal.valueOf(rateIntensity).setScale(4, RoundingMode.HALF_UP))
        .setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * Стоимость госпитализации для
   * случаев лекарственной
   * терапии федеральных МО.
   *
   * <p>Тариф на оплату медицинской помощи,
   * оказываемой федеральной медицинской
   * организацией в условиях при
   * противоопухолевой лекарственной
   * терапии злокачественных новообразований:
   *
   * <p>T = НФЗ*КБС*КЗ*((1 - Дзп) + Дзп*КД*КС*КСЛП)
   * - для медицинской организации, функции
   * и полномочия учредителей в отношении
   * которых осуществляет Правительство
   * Российской Федерации или федеральные
   * органы исполнительной власти
   *
   * <p>НФЗ - средний норматив финансовых
   * затрат на единицу объема
   * предоставления медицинской помощи
   * в z-х условиях, оказываемой
   * федеральными медицинскими
   * организациями, установленный Программой;
   *
   * <p>КБС - коэффициент приведения
   * среднего норматива финансовых затрат
   * на единицу объема предоставления
   * медицинской помощи в z-х условиях к
   * базовой ставке, исключающей влияние
   * применяемых коэффициентов относительной
   * затратоемкости и специфики оказания
   * медицинской помощи, коэффициента
   * дифференциации и коэффициента сложности
   * лечения пациентов, принимающий значение
   * 0,41 - для стационара
   * и 0,52 - для дневного стационара;
   * financialCost - НФЗ
   * baseRate - КБС
   *
   * @param valueCdt значение КСЛП
   * @param miById   {@link MedicalInstitution}
   * @param drg      {@link DiagnosisRelatedGroups}
   * @return стоимость ЛТ
   * @throws BadRequestException если некорректные данные
   *                             если КС = 0
   */
  private BigDecimal getCostTreatmentFederal(
      final Float valueCdt,
      final MedicalInstitution miById,
      final DiagnosisRelatedGroups drg) {
    final float rateIntensity = drg.getRateIntensity();
    final float coeffSpecificity = CoefficientSpecificityUtils
        .calculate(rateIntensity, miById.getTypeMi());
    if (coeffSpecificity == 0) {
      throw new BadRequestException("Значение Коэффициента специфики не определено");
    }
    final String numberDrg = drg.getNumberDrg();
    double financialCost;
    double baseRate;
    if (numberDrg
        .toLowerCase(Locale.ROOT)
        .startsWith(CoefficientDifficultyTreatingService.ROUND_THE_CLOCK_CARE_FACILITY
            .toLowerCase(Locale.ROOT))) {
      financialCost = FINANCIAL_COST_STANDARD_ROUND_THE_CLOCK_CARE_FACILITY;
      baseRate = COEFFICIENT_BASE_RATE_ROUND_THE_CLOCK_CARE_FACILITY;
    } else if (numberDrg
        .toLowerCase(Locale.ROOT)
        .startsWith(CoefficientDifficultyTreatingService.DAY_CARE_FACILITY
            .toLowerCase(Locale.ROOT))) {
      financialCost = FINANCIAL_COST_STANDARD_DAY_CARE_FACILITY;
      baseRate = COEFFICIENT_BASE_RATE_DAY_CARE_FACILITY;
    } else {
      throw new BadRequestException(
          "Номер КСГ не верный: " + numberDrg);
    }
    final float wageShare = drg.getWageShare();
    final BigDecimal bdWageShare = BigDecimal
        .valueOf(wageShare)
        .setScale(4, RoundingMode.HALF_UP);
    final float diffCoefficient = miById.getDiffCoefficient();
    return bdWageShare
        .multiply(BigDecimal.valueOf(coeffSpecificity))
        .multiply(BigDecimal.valueOf(valueCdt))
        .multiply(BigDecimal.valueOf(diffCoefficient))
        .add(BigDecimal.ONE)
        .subtract(bdWageShare)
        .multiply(BigDecimal.valueOf(financialCost).setScale(2, RoundingMode.HALF_UP))
        .multiply(BigDecimal.valueOf(baseRate).setScale(2, RoundingMode.HALF_UP))
        .multiply(BigDecimal.valueOf(rateIntensity).setScale(4, RoundingMode.HALF_UP))
        .setScale(2, RoundingMode.HALF_UP);
  }
}