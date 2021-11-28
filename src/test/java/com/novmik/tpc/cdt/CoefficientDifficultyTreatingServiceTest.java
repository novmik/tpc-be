package com.novmik.tpc.cdt;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.SubjectOfRF;
import com.novmik.tpc.subject.SubjectOfRFService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.novmik.tpc.cdt.CdtConstant.*;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoefficientDifficultyTreatingServiceTest {

    @Mock
    private CoefficientDifficultyTreatingRepository cdtRepository;
    @Mock
    private CaseCdtService caseCdtService;
    @Mock
    private SubjectOfRFService subjectOfRFService;
    private CoefficientDifficultyTreatingService underTest;

    @BeforeEach
    void setUp() { underTest = new CoefficientDifficultyTreatingService(cdtRepository, caseCdtService, subjectOfRFService); }

    @Test
    void canGetCareFacilityCdtpListBySubjectId() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                1L,
                "Test SubjectName",
                22222.00D,
                22222.00D
        );
        long id = 10;
        String careFacility = "testCareFacility";
        given(subjectOfRFService.getSubjectById(id)).willReturn(Optional.of(subjectOfRF));
        underTest.getCareFacilityCdtpListBySubjectId(id, careFacility);
        verify(cdtRepository).findAllByNameSubjectAndCareFacility(subjectOfRF.getNameSubject(), careFacility);
    }

    @Test
    void willThrowWhenSubjectIdNotExists() {
        long id = 10;
        String careFacility = "testCareFacility";
        when(subjectOfRFService.getSubjectById(id)).thenThrow(new NotFoundException(SUBJECT_NOT_EXISTS + id));
        assertThatThrownBy(() -> underTest.getCareFacilityCdtpListBySubjectId(id,careFacility))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SUBJECT_NOT_EXISTS + id);
        verify(cdtRepository, never()).findAllByNameSubjectAndCareFacility("Test NameSubject", careFacility);
    }

    @Test
    void canAddNewCDTPWithST() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
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
                subjectOfRF.getNameSubject(),
                2F,
                ROUND_THE_CLOCK_CARE_FACILITY
        );
        ArgumentCaptor<CoefficientDifficultyTreating> cdtpArgumentCaptor = ArgumentCaptor.forClass(CoefficientDifficultyTreating.class);
        given(subjectOfRFService.findByNameSubject(subjectOfRF.getNameSubject())).willReturn(Optional.of(subjectOfRF));
        given(caseCdtService.save(caseCdt.getNominationCaseCdt())).willReturn(caseCdt);
        underTest.addNewCoefficientDifficultyTreating(cdt);
        verify(cdtRepository).save(cdtpArgumentCaptor.capture());
        CoefficientDifficultyTreating capturedCdt = cdtpArgumentCaptor.getValue();
        assertThat(capturedCdt).isEqualTo(cdt);
    }

    @Test
    void canAddNewCDTPWithDS() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
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
                subjectOfRF.getNameSubject(),
                2F,
                DAY_CARE_FACILITY
        );
        ArgumentCaptor<CoefficientDifficultyTreating> cdtpArgumentCaptor = ArgumentCaptor.forClass(CoefficientDifficultyTreating.class);
        given(subjectOfRFService.findByNameSubject(subjectOfRF.getNameSubject())).willReturn(Optional.of(subjectOfRF));
        given(caseCdtService.save(caseCdt.getNominationCaseCdt())).willReturn(caseCdt);
        underTest.addNewCoefficientDifficultyTreating(cdt);
        verify(cdtRepository).save(cdtpArgumentCaptor.capture());
        CoefficientDifficultyTreating capturedCdt = cdtpArgumentCaptor.getValue();
        assertThat(capturedCdt).isEqualTo(cdt);
    }

    @Test
    void willThrowWhenAddNewNullCdt() {
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating();
        assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(CDT_NOT_CORRECT + cdt);
        verify(cdtRepository, never()).save(cdt);
    }

    @Test
    void willThrowAddNewCdtWhenCaseCdtIsExists () {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
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
        given(subjectOfRFService.findByNameSubject(cdt.getNameSubject())).willReturn(Optional.of(subjectOfRF));
        given(caseCdtService.save(cdt.getCaseCdt().getNominationCaseCdt())).willReturn(caseCdt);
        given(cdtRepository.existByCaseCdtIdAndNameSubjectAndCareFacility(cdt.getCaseCdt().getId(), cdt.getNameSubject(), cdt.getCareFacility())).willReturn(true);
        assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(CDT_EXISTS + caseCdt);
        verify(cdtRepository, never()).save(cdt);
    }

    @Test
    void willThrowAddNewCDTPWhenValueCDTPNotCorrect () {
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
                1L,
                new CaseCdt(1, "Test NominationCaseCdt"),
                "Test NameSubject",
                0F,
                ROUND_THE_CLOCK_CARE_FACILITY
        );
        assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(CDT_VALUE_NOT_CORRECT + cdt.getValueNominationCaseCdt());
        verify(cdtRepository, never()).save(cdt);
    }

    @Test
    void willThrowAddNewCDTPWhenCareFacilityNotCorrect() {
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
                1L,
                new CaseCdt(1, "Test NominationCaseCdt"),
                "Test NameSubject",
                1F,
                "STDS"
        );
        assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(CARE_FACILITY_NOT_CORRECT + cdt.getCareFacility() + ". " + CARE_FACILITY_MUST_BE + ROUND_THE_CLOCK_CARE_FACILITY + " или " + DAY_CARE_FACILITY);
        verify(cdtRepository, never()).save(cdt);
    }

    @Test
    void willThrowAddNewCDTPWhenSubjectIdNotExists() {
        CoefficientDifficultyTreating cdt = new CoefficientDifficultyTreating(
                1L,
                new CaseCdt(1, "Test NominationCaseCdt"),
                "Test NameSubject",
                1F,
                "ST"
        );
        given(subjectOfRFService.findByNameSubject(cdt.getNameSubject())).willReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.addNewCoefficientDifficultyTreating(cdt))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SUBJECT_NOT_EXISTS + cdt.getNameSubject());
        verify(cdtRepository, never()).save(cdt);
    }

}