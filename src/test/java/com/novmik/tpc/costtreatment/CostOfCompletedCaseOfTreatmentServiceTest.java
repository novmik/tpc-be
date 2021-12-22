package com.novmik.tpc.costtreatment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.novmik.tpc.drg.DiagnosisRelatedGroups;
import com.novmik.tpc.drg.DiagnosisRelatedGroupsService;
import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.medicalinstitution.MedicalInstitution;
import com.novmik.tpc.medicalinstitution.MedicalInstitutionService;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CostOfCompletedCaseOfTreatmentServiceTest {

  @Mock
  private MedicalInstitutionService miService;
  @Mock
  private DiagnosisRelatedGroupsService drgService;
  @Mock
  private SubjectService subjectService;
  private CostOfCompletedCaseOfTreatmentService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CostOfCompletedCaseOfTreatmentService(miService, drgService, subjectService);
  }

  @Test
  void willThrowWhenGetCostTreatmentWithDrgAndParametersIncorrect() {
    CostTreatmentRequest costRequest = new CostTreatmentRequest(0L, "", 1.0F);
    assertThatThrownBy(() -> underTest.getCostTreatmentWithDrg(costRequest))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Субъект id/Номер КСГ/Значение КСГ указаны неверно: " + costRequest);
  }

  @Test
  void canGetCostTreatmentWithDrgNotFederalSt() {
    CostTreatmentRequest costRequest = new CostTreatmentRequest(170L, "st19.001", 1.0F);
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        "Test NameSubject",
        "Test NameMi",
        1F,
        "1.1",
        1.2F,
        "1.2",
        1.1F,
        0);
    DiagnosisRelatedGroups drg = new DiagnosisRelatedGroups(
        costRequest.getNumberDrg(), "Test Nomination DRG", 1.7F, 0.5F);
    Subject subject = new Subject("Test Name Subject", 12_000D, 9_000D);
    when(miService.getMedicalInstitutionById(anyLong())).thenReturn(medicalInstitution);
    when(drgService.byNumberDrg(costRequest.getNumberDrg())).thenReturn(Optional.of(drg));
    when(subjectService.findByNameSubject(medicalInstitution.getNameSubject()))
        .thenReturn(Optional.of(subject));
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getCostTreatment())
        .isEqualTo("22440.00");
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getDrg()).isNotNull();
  }

  @Test
  void canGetCostTreatmentWithDrgNotFederalDs() {
    CostTreatmentRequest costRequest = new CostTreatmentRequest(170L, "ds19.001", 1.0F);
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        "Test NameSubject",
        "Test NameMi",
        1F,
        "1.1",
        1.2F,
        "1.2",
        1.1F,
        0);
    DiagnosisRelatedGroups drg = new DiagnosisRelatedGroups(
        costRequest.getNumberDrg(), "Test Nomination DRG", 1.7F, 0.5F);
    Subject subject = new Subject("Test Name Subject", 12_000D, 9_000D);
    when(miService.getMedicalInstitutionById(anyLong())).thenReturn(medicalInstitution);
    when(drgService.byNumberDrg(costRequest.getNumberDrg())).thenReturn(Optional.of(drg));
    when(subjectService.findByNameSubject(medicalInstitution.getNameSubject()))
        .thenReturn(Optional.of(subject));
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getCostTreatment())
        .isEqualTo("16065.00");
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getDrg()).isNotNull();
  }

  @Test
  void canGetCostTreatmentWithDrgFederalSt() {
    CostTreatmentRequest costRequest = new CostTreatmentRequest(170L, "st19.001", 1.0F);
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        "Test NameSubject",
        "Test NameMi",
        1F,
        "1.1",
        1.2F,
        "1.2",
        1.1F,
        3);
    DiagnosisRelatedGroups drg = new DiagnosisRelatedGroups(
        costRequest.getNumberDrg(), "Test Nomination DRG", 1.7F, 0.5F);
    when(miService.getMedicalInstitutionById(anyLong())).thenReturn(medicalInstitution);
    when(drgService.byNumberDrg(costRequest.getNumberDrg())).thenReturn(Optional.of(drg));
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getCostTreatment())
        .isEqualTo("39506.59");
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getDrg()).isNotNull();
  }

  @Test
  void canGetCostTreatmentWithDrgFederalDs() {
    CostTreatmentRequest costRequest = new CostTreatmentRequest(170L, "ds19.001", 1.0F);
    MedicalInstitution medicalInstitution = new MedicalInstitution(
        "Test NameSubject",
        "Test NameMi",
        1F,
        "1.1",
        1.2F,
        "1.2",
        1.1F,
        3);
    DiagnosisRelatedGroups drg = new DiagnosisRelatedGroups(
        costRequest.getNumberDrg(), "Test Nomination DRG", 1.7F, 0.5F);
    when(miService.getMedicalInstitutionById(anyLong())).thenReturn(medicalInstitution);
    when(drgService.byNumberDrg(costRequest.getNumberDrg())).thenReturn(Optional.of(drg));
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getCostTreatment())
        .isEqualTo("22645.69");
    assertThat(underTest.getCostTreatmentWithDrg(costRequest).getDrg()).isNotNull();
  }
}