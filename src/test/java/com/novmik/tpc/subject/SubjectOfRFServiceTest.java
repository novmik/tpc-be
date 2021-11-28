package com.novmik.tpc.subject;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.novmik.tpc.subject.SubjectConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubjectOfRFServiceTest {

    @Mock
    private SubjectOfRFRepository subjectOfRFRepository;
    private SubjectOfRFService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SubjectOfRFService(subjectOfRFRepository);
    }

    @Test
    void canGetSubjectByNameSubject() {
        String nameSubject = "Test SubjectName";
        underTest.findByNameSubject(nameSubject);
        verify(subjectOfRFRepository).findByNameSubject(nameSubject);
    }

    @Test
    void canGetBooleanWhenSubjectExistById() {
        long id = 10;
        underTest.existsById(id);
        verify(subjectOfRFRepository).existsById(id);
    }

    @Test
    void canGetTrueWhenSubjectExistById() {
        long id = 10;
        given(subjectOfRFRepository.existsById(id)).willReturn(true);
        assertThat(underTest.existsById(id)).isTrue();
    }

    @Test
    void canGetFalseWhenSubjectNotExistById() {
        long id = 10;
        given(subjectOfRFRepository.existsById(id)).willReturn(false);
        assertThat(underTest.existsById(id)).isFalse();
    }

    @Test
    void canGetIdAndNameSubjectList() {
        underTest.getIdAndNameSubjectFromTable();
        verify(subjectOfRFRepository).getIdAndNameSubjectFromTable();
    }

    @Test
    void canGetSubjectById() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                1L,
                "Test SubjectName",
                22222.00D,
                22222.00D
        );
        given(subjectOfRFRepository.findById(subjectOfRF.getId())).willReturn(Optional.of(subjectOfRF));
        underTest.getSubjectById(subjectOfRF.getId());
        verify(subjectOfRFRepository).findById(subjectOfRF.getId());
    }

    @Test
    void canAddNewSubject() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                1L,
                "Test SubjectName",
                22222.00D,
                22222.00D
        );
        underTest.addNewSubject(subjectOfRF);
        ArgumentCaptor<SubjectOfRF> subjectOfRFArgumentCaptor = ArgumentCaptor.forClass(SubjectOfRF.class);
        verify(subjectOfRFRepository).save(subjectOfRFArgumentCaptor.capture());
        SubjectOfRF capturedSubject = subjectOfRFArgumentCaptor.getValue();
        assertThat(capturedSubject).isEqualTo(subjectOfRF);
    }

    @Test
    void willThrowWhenAddNewNullSubject() {
        SubjectOfRF subjectOfRF = new SubjectOfRF();
        assertThatThrownBy(() -> underTest.addNewSubject(subjectOfRF))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(SUBJECT_NOT_CORRECT);
        verify(subjectOfRFRepository, never()).save(subjectOfRF);
    }

    @Test
    void willThrowWhenAddSubjectWhichExists() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                "Test SubjectName",
                22222.00D,
                22222.00D
        );
        given(subjectOfRFRepository.findByNameSubject(subjectOfRF.getNameSubject())).willReturn(Optional.of(subjectOfRF));
        assertThatThrownBy(() -> underTest.addNewSubject(subjectOfRF))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(SUBJECT_EXISTS + subjectOfRF.getNameSubject());
        verify(subjectOfRFRepository, never()).save(subjectOfRF);
    }

    @Test
    void canUpdateSubject() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                1L,
                "Test SubjectName",
                1.00D,
                1.00D
        );
        given(subjectOfRFRepository.existsById(subjectOfRF.getId())).willReturn(true);
        underTest.updateSubject(subjectOfRF);
        verify(subjectOfRFRepository).save(subjectOfRF);
    }

    @Test
    void willThrowWhenUpdateSubjectIsNull() {
        SubjectOfRF subjectOfRF = new SubjectOfRF();
        assertThatThrownBy(() -> underTest.updateSubject(subjectOfRF))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(SUBJECT_NOT_CORRECT);
        verify(subjectOfRFRepository, never()).save(subjectOfRF);
    }

    @Test
    void willThrowWhenUpdateSubjectWhichNotExists() {
        SubjectOfRF subjectOfRF = new SubjectOfRF(
                1L,
                "Test SubjectName",
                1.00D,
                1.00D
        );
        given(subjectOfRFRepository.existsById(subjectOfRF.getId())).willReturn(false);
        assertThatThrownBy(() -> underTest.updateSubject(subjectOfRF))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SUBJECT_NOT_EXISTS + subjectOfRF.getId());
        verify(subjectOfRFRepository, never()).save(subjectOfRF);
    }

    @Test
    void canDeleteSubjectById() {
        long id = 10;
        given(subjectOfRFRepository.existsById(id)).willReturn(true);
        underTest.deleteSubjectById(id);
        verify(subjectOfRFRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteSubjectByIdWhichNotExists() {
        long id = 10;
        given(subjectOfRFRepository.existsById(id)).willReturn(false);
        assertThatThrownBy(() -> underTest.deleteSubjectById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SUBJECT_NOT_EXISTS + id);
        verify(subjectOfRFRepository, never()).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteNullSubjectById() {
        assertThatThrownBy(() -> underTest.deleteSubjectById(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(SUBJECT_NOT_CORRECT);
        verify(subjectOfRFRepository, never()).deleteById(anyLong());
    }
}