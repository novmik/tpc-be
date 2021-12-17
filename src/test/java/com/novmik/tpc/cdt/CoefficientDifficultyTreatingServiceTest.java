package com.novmik.tpc.cdt;

import static com.novmik.tpc.cdt.CoefficientDifficultyTreatingService.DAY_CARE_FACILITY;
import static com.novmik.tpc.cdt.CoefficientDifficultyTreatingService.ROUND_THE_CLOCK_CARE_FACILITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoefficientDifficultyTreatingServiceTest {

  @Mock
  private CoefficientDifficultyTreatingRepository cdtRepository;
  @Mock
  private CaseCdtService caseCdtService;
  @Mock
  private SubjectService subjectService;
  private CoefficientDifficultyTreatingService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CoefficientDifficultyTreatingService(cdtRepository, caseCdtService,
        subjectService);
  }

  @Test
  void canGetCareFacilityCdtpListBySubjectId() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    long id = 10;
    String careFacility = "testCareFacility";
    given(subjectService.getSubjectById(id)).willReturn(subject);
    underTest.getCareFacilityCdtListBySubjectId(id, careFacility);
    verify(cdtRepository).findAllByNameSubjectAndCareFacility(subject.getNameSubject(),
        careFacility);
  }

  @Test
  void willThrowWhenSubjectIdNotExists() {
    long id = 10;
    String careFacility = "testCareFacility";
    when(subjectService.getSubjectById(id)).thenThrow(
        new NotFoundException("Субъекта с таким id/именем/названием не существует: " + id));
    assertThatThrownBy(() -> underTest.getCareFacilityCdtListBySubjectId(id, careFacility))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Субъекта с таким id/именем/названием не существует: " + id);
    verify(cdtRepository, never()).findAllByNameSubjectAndCareFacility("Test NameSubject",
        careFacility);
  }

  @Test
  void canAddNewCdtWithSt() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    CaseCdt caseCdt = new CaseCdt(
        1,
        "Test NominationCaseCdt"
    );
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
        1L,
        caseCdt,
        subject.getNameSubject(),
        2F,
        ROUND_THE_CLOCK_CARE_FACILITY
    );
    given(subjectService.findByNameSubject(subject.getNameSubject())).willReturn(
        Optional.of(subject));
    given(caseCdtService.save(caseCdt.getNominationCaseCdt())).willReturn(caseCdt);
    underTest.addNewCoefficientDifficultyTreating(cdt);
    ArgumentCaptor<CoefficientDifficultyTreating> cdtArgumentCaptor = ArgumentCaptor.forClass(
        CoefficientDifficultyTreating.class);
    verify(cdtRepository).save(cdtArgumentCaptor.capture());
    CoefficientDifficultyTreating capturedCdt = cdtArgumentCaptor.getValue();
    assertThat(capturedCdt).isEqualTo(cdt);
  }

  @Test
  void canAddNewCdtWithDs() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    CaseCdt caseCdt = new CaseCdt(
        1,
        "Test NominationCaseCdt"
    );
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
        1L,
        caseCdt,
        subject.getNameSubject(),
        2F,
        DAY_CARE_FACILITY
    );
    given(subjectService.findByNameSubject(subject.getNameSubject())).willReturn(
        Optional.of(subject));
    given(caseCdtService.save(caseCdt.getNominationCaseCdt())).willReturn(caseCdt);
    underTest.addNewCoefficientDifficultyTreating(cdt);
    ArgumentCaptor<CoefficientDifficultyTreating> cdtArgumentCaptor = ArgumentCaptor.forClass(
        CoefficientDifficultyTreating.class);
    verify(cdtRepository).save(cdtArgumentCaptor.capture());
    CoefficientDifficultyTreating capturedCdt = cdtArgumentCaptor.getValue();
    assertThat(capturedCdt).isEqualTo(cdt);
  }

  @Test
  void willThrowWhenAddNewNullCdt() {
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating();
    assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректные данные о КСЛП." + cdt);
    verify(cdtRepository, never()).save(cdt);
  }

  @Test
  void willThrowAddNewCdtWhenCaseCdtIsExists() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    CaseCdt caseCdt = new CaseCdt(
        1,
        "Test NominationCaseCdt"
    );
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
        1L,
        caseCdt,
        "Test NameSubject",
        2F,
        ROUND_THE_CLOCK_CARE_FACILITY
    );
    given(subjectService.findByNameSubject(cdt.getNameSubject())).willReturn(Optional.of(subject));
    given(caseCdtService.save(cdt.getCaseCdt().getNominationCaseCdt())).willReturn(caseCdt);
    given(cdtRepository.existByCaseCdtIdAndNameSubjectAndCareFacility(cdt.getCaseCdt().getIdCaseCdt(),
        cdt.getNameSubject(), cdt.getCareFacility())).willReturn(true);
    assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("КСЛП с таким id/именем/названием уже существует: " + caseCdt);
    verify(cdtRepository, never()).save(cdt);
  }

  @Test
  void willThrowAddNewCdtWhenValueCdtNotCorrect() {
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
        1L,
        new CaseCdt(1, "Test NominationCaseCdt"),
        "Test NameSubject",
        0F,
        ROUND_THE_CLOCK_CARE_FACILITY
    );
    assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректное значение КСЛП: " + cdt.getValue());
    verify(cdtRepository, never()).save(cdt);
  }

  @Test
  void willThrowAddNewCdtWhenCareFacilityNotCorrect() {
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
        1L,
        new CaseCdt(1, "Test NominationCaseCdt"),
        "Test NameSubject",
        1F,
        "STDS"
    );
    assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(String.format(
            "Некорректное значение стационара: %s. Должно быть: %s или %s",
            cdt.getCareFacility(),
            ROUND_THE_CLOCK_CARE_FACILITY,
            DAY_CARE_FACILITY));
    verify(cdtRepository, never()).save(cdt);
  }

  @Test
  void willThrowAddNewCdtWhenSubjectIdNotExists() {
    CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
        1L,
        new CaseCdt(1, "Test NominationCaseCdt"),
        "Test NameSubject",
        1F,
        "ST"
    );
    given(subjectService.findByNameSubject(cdt.getNameSubject())).willReturn(Optional.empty());
    assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Субъекта с таким id/именем/названием не существует: " + cdt.getNameSubject());
    verify(cdtRepository, never()).save(cdt);
  }

}