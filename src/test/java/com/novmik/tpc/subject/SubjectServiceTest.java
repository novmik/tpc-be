package com.novmik.tpc.subject;

import static com.novmik.tpc.subject.SubjectConstants.SUBJECT_EXISTS;
import static com.novmik.tpc.subject.SubjectConstants.SUBJECT_NOT_CORRECT;
import static com.novmik.tpc.subject.SubjectConstants.SUBJECT_NOT_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

  @Mock
  private SubjectRepository subjectRepository;
  private SubjectService underTest;

  @BeforeEach
  void setUp() {
    underTest = new SubjectService(subjectRepository);
  }

  @Test
  void canGetSubjectByNameSubject() {
    String nameSubject = "Test SubjectName";
    underTest.findByNameSubject(nameSubject);
    verify(subjectRepository).findByNameSubject(nameSubject);
  }

  @Test
  void canGetBooleanWhenSubjectExistById() {
    long id = 10;
    underTest.existsById(id);
    verify(subjectRepository).existsById(id);
  }

  @Test
  void canGetTrueWhenSubjectExistById() {
    long id = 10;
    given(subjectRepository.existsById(id)).willReturn(true);
    assertThat(underTest.existsById(id)).isTrue();
  }

  @Test
  void canGetFalseWhenSubjectNotExistById() {
    long id = 10;
    given(subjectRepository.existsById(id)).willReturn(false);
    assertThat(underTest.existsById(id)).isFalse();
  }

  @Test
  void canGetIdAndNameSubjectList() {
    underTest.getIdAndNameSubjectFromTable();
    verify(subjectRepository).getIdAndNameSubjectFromTable();
  }

  @Test
  void canGetSubjectById() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    given(subjectRepository.findById(subject.getIdSubject())).willReturn(Optional.of(subject));
    underTest.getSubjectById(subject.getIdSubject());
    verify(subjectRepository).findById(subject.getIdSubject());
  }

  @Test
  void canAddNewSubject() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    underTest.addNewSubject(subject);
    ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
    verify(subjectRepository).save(subjectArgumentCaptor.capture());
    Subject capturedSubject = subjectArgumentCaptor.getValue();
    assertThat(capturedSubject).isEqualTo(subject);
  }

  @Test
  void willThrowWhenAddNewNullSubject() {
    Subject subject = new Subject();
    assertThatThrownBy(() -> underTest.addNewSubject(subject))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(SUBJECT_NOT_CORRECT);
    verify(subjectRepository, never()).save(subject);
  }

  @Test
  void willThrowWhenAddSubjectWhichExists() {
    Subject subject = new Subject(
        "Test SubjectName",
        22222.00D,
        22222.00D
    );
    given(subjectRepository.findByNameSubject(subject.getNameSubject())).willReturn(
        Optional.of(subject));
    assertThatThrownBy(() -> underTest.addNewSubject(subject))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(SUBJECT_EXISTS + subject.getNameSubject());
    verify(subjectRepository, never()).save(subject);
  }

  @Test
  void canUpdateSubject() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        1.00D,
        1.00D
    );
    given(subjectRepository.existsById(subject.getIdSubject())).willReturn(true);
    underTest.updateSubject(subject);
    verify(subjectRepository).save(subject);
  }

  @Test
  void willThrowWhenUpdateSubjectIsNull() {
    Subject subject = new Subject();
    assertThatThrownBy(() -> underTest.updateSubject(subject))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(SUBJECT_NOT_CORRECT);
    verify(subjectRepository, never()).save(subject);
  }

  @Test
  void willThrowWhenUpdateSubjectWhichNotExists() {
    Subject subject = new Subject(
        1L,
        "Test SubjectName",
        1.00D,
        1.00D
    );
    given(subjectRepository.existsById(subject.getIdSubject())).willReturn(false);
    assertThatThrownBy(() -> underTest.updateSubject(subject))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(SUBJECT_NOT_EXISTS + subject.getIdSubject());
    verify(subjectRepository, never()).save(subject);
  }

  @Test
  void canDeleteSubjectById() {
    long id = 10;
    given(subjectRepository.existsById(id)).willReturn(true);
    underTest.deleteSubjectById(id);
    verify(subjectRepository).deleteById(id);
  }

  @Test
  void willThrowWhenDeleteSubjectByIdWhichNotExists() {
    long id = 10;
    given(subjectRepository.existsById(id)).willReturn(false);
    assertThatThrownBy(() -> underTest.deleteSubjectById(id))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(SUBJECT_NOT_EXISTS + id);
    verify(subjectRepository, never()).deleteById(id);
  }

  @Test
  void willThrowWhenDeleteNullSubjectById() {
    assertThatThrownBy(() -> underTest.deleteSubjectById(null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(SUBJECT_NOT_CORRECT);
    verify(subjectRepository, never()).deleteById(anyLong());
  }
}