package com.novmik.tpc.medicalinstitution;

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

import java.util.List;
import java.util.Optional;

import static com.novmik.tpc.medicalinstitution.MedicalInstitutionConstant.*;
import static com.novmik.tpc.subject.SubjectConstant.SUBJECT_NOT_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MedicalInstitutionServiceTest {

    @Mock
    private MedicalInstitutionRepository medicalInstitutionRepository;
    @Mock
    private SubjectOfRFService subjectOfRFService;
    private MedicalInstitutionService underTest;
    @Mock
    private List<NameMedicalInstitutionAndId> nameMedicalInstitutionAndIdList;

    @BeforeEach
    void setUp() {
        underTest = new MedicalInstitutionService(medicalInstitutionRepository, subjectOfRFService);
    }

    @Test
    void canGetMedicalInstitutionList() {
        long idSubject = 10;
        given(medicalInstitutionRepository.listIdAndMedicalInstitutionNameBySubjectId(idSubject)).willReturn(nameMedicalInstitutionAndIdList);
        given(nameMedicalInstitutionAndIdList.isEmpty()).willReturn(false);
        underTest.getMedicalInstitutionList(idSubject);
        verify(medicalInstitutionRepository).listIdAndMedicalInstitutionNameBySubjectId(idSubject);
    }

    @Test
    void willThrowWhenGetMedicalInstitutionListBySubjectId() {
        long idSubject = 10;
        assertThatThrownBy(() -> underTest.getMedicalInstitutionList(idSubject))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MEDICAL_INSTITUTIONS_NOT_EXISTS_BY_ID_SUBJECT + idSubject);
    }

    @Test
    void canGetMedicalInstitutionById() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                1L,
                "Test SubjectName",
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0
        );
        given(medicalInstitutionRepository.findById(medicalInstitution.getId())).willReturn(Optional.of(medicalInstitution));
        underTest.getMedicalInstitutionById(medicalInstitution.getId());
        verify(medicalInstitutionRepository).findById(medicalInstitution.getId());
    }

    @Test
    void canAddNewMedicalInstitution() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                1L,
                "Test SubjectName",
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0
        );
        given(subjectOfRFService.findByNameSubject(medicalInstitution.getNameSubject())).willReturn(Optional.of(new SubjectOfRF()));
        underTest.addNewMedicalInstitution(medicalInstitution);
        ArgumentCaptor<MedicalInstitution> medicalInstitutionArgumentCaptor = ArgumentCaptor.forClass(MedicalInstitution.class);
        verify(medicalInstitutionRepository).save(medicalInstitutionArgumentCaptor.capture());
        MedicalInstitution capturedMedicalInstitution = medicalInstitutionArgumentCaptor.getValue();
        assertThat(capturedMedicalInstitution).isEqualTo(medicalInstitution);
    }

    @Test
    void willThrowWhenAddNewNullMedicalInstitution() {
        MedicalInstitution medicalInstitution = new MedicalInstitution();
        assertThatThrownBy(() -> underTest.addNewMedicalInstitution(medicalInstitution))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(MEDICAL_INSTITUTION_NOT_CORRECT);
        verify(medicalInstitutionRepository, never()).save(medicalInstitution);
    }

    @Test
    void willThrowWhenAddMedicalInstitutionWhichExists() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                1L,
                "Test SubjectName",
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0
        );
        given(medicalInstitutionRepository.findByNameMedicalInstitutionAndNameSubject(medicalInstitution.getNameMedicalInstitution(), medicalInstitution.getNameSubject())).willReturn(Optional.of(medicalInstitution));
        assertThatThrownBy(() -> underTest.addNewMedicalInstitution(medicalInstitution))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEDICAL_INSTITUTION_EXISTS + medicalInstitution.getNameMedicalInstitution());
        verify(medicalInstitutionRepository, never()).save(medicalInstitution);
    }

    @Test
    void willThrowWhenAddMedicalInstitutionWithSubjectNotExists() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                1L,
                "Test SubjectName",
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0
        );
        given(medicalInstitutionRepository.findByNameMedicalInstitutionAndNameSubject(medicalInstitution.getNameMedicalInstitution(), medicalInstitution.getNameSubject())).willReturn(Optional.empty());
        given(subjectOfRFService.findByNameSubject(medicalInstitution.getNameSubject())).willReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.addNewMedicalInstitution(medicalInstitution))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SUBJECT_NOT_EXISTS + medicalInstitution.getNameSubject());
        verify(medicalInstitutionRepository, never()).save(medicalInstitution);
    }

    @Test
    void canUpdateMedicalInstitution() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                1L,
                "Test SubjectName",
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0
        );
        given(medicalInstitutionRepository.existsById(medicalInstitution.getId())).willReturn(true);
        underTest.updateMedicalInstitution(medicalInstitution);
        verify(medicalInstitutionRepository).save(medicalInstitution);
    }

    @Test
    void willThrowWhenUpdateMedicalInstitutionIsNull() {
        MedicalInstitution medicalInstitution = new MedicalInstitution();
        assertThatThrownBy(() -> underTest.updateMedicalInstitution(medicalInstitution))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(MEDICAL_INSTITUTION_NOT_CORRECT);
        verify(medicalInstitutionRepository, never()).save(medicalInstitution);
    }

    @Test
    void willThrowWhenUpdateMedicalInstitutionByIdNotExists() {
        MedicalInstitution medicalInstitution = new MedicalInstitution(
                1L,
                "Test SubjectName",
                "Test MedicalInstitutionName",
                2F,
                "1",
                2F,
                "1",
                2F,
                0
        );
        given(medicalInstitutionRepository.existsById(medicalInstitution.getId())).willReturn(false);
        assertThatThrownBy(() -> underTest.updateMedicalInstitution(medicalInstitution))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEDICAL_INSTITUTIONS_NOT_EXISTS + medicalInstitution.getId());
        verify(medicalInstitutionRepository, never()).save(medicalInstitution);
    }

    @Test
    void canDeleteMedicalInstitutionById() {
        long id = 10;
        given(medicalInstitutionRepository.existsById(id)).willReturn(true);
        underTest.deleteMedicalInstitutionById(id);
        verify(medicalInstitutionRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteMedicalInstitutionByIdWhichNotExists() {
        long id = 10;
        given(medicalInstitutionRepository.existsById(id)).willReturn(false);
        assertThatThrownBy(() -> underTest.deleteMedicalInstitutionById(id))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEDICAL_INSTITUTIONS_NOT_EXISTS + id);
        verify(medicalInstitutionRepository, never()).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteNullMedicalInstitutionById() {
        assertThatThrownBy(() -> underTest.deleteMedicalInstitutionById(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEDICAL_INSTITUTION_NOT_CORRECT);
        verify(medicalInstitutionRepository, never()).deleteById(anyLong());
    }


}