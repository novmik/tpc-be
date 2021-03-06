package com.novmik.tpc.medicalinstitution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.subject.Subject;
import com.novmik.tpc.subject.SubjectService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicalInstitutionServiceTest {

  @Mock
  private MedicalInstitutionRepository medicalInstitutionRepository;
  @Mock
  private SubjectService subjectService;
  private MedicalInstitutionService underTest;
  @Mock
  private List<NameMedicalInstitutionAndId> nameMedicalInstitutionAndIdList;

  @BeforeEach
  void setUp() {
    underTest = new MedicalInstitutionService(medicalInstitutionRepository, subjectService);
  }

  @Test
  void canGetMedicalInstitutionList() {
    long idSubject = 10;
    given(medicalInstitutionRepository.listIdAndMedicalInstitutionNameBySubjectId(
        idSubject)).willReturn(nameMedicalInstitutionAndIdList);
    given(nameMedicalInstitutionAndIdList.isEmpty()).willReturn(false);
    underTest.getMedicalInstitutionList(idSubject);
    verify(medicalInstitutionRepository).listIdAndMedicalInstitutionNameBySubjectId(idSubject);
  }

  @Test
  void willThrowWhenGetMedicalInstitutionListBySubjectId() {
    long idSubject = 10;
    assertThatThrownBy(() -> underTest.getMedicalInstitutionList(idSubject))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Нет медицинских организаций с таким id Субъекта: " + idSubject);
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
    given(medicalInstitutionRepository.findById(medicalInstitution.getIdMi())).willReturn(
        Optional.of(medicalInstitution));
    underTest.getMedicalInstitutionById(medicalInstitution.getIdMi());
    verify(medicalInstitutionRepository).findById(medicalInstitution.getIdMi());
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
    given(subjectService.findByNameSubject(medicalInstitution.getNameSubject())).willReturn(
        Optional.of(new Subject()));
    underTest.addNewMedicalInstitution(medicalInstitution);
    ArgumentCaptor<MedicalInstitution> medicalInstitutionArgumentCaptor = ArgumentCaptor.forClass(
        MedicalInstitution.class);
    verify(medicalInstitutionRepository).save(medicalInstitutionArgumentCaptor.capture());
    MedicalInstitution capturedMedicalInstitution = medicalInstitutionArgumentCaptor.getValue();
    assertThat(capturedMedicalInstitution).isEqualTo(medicalInstitution);
  }

  @Test
  void willThrowWhenAddNewNullMedicalInstitution() {
    MedicalInstitution medicalInstitution = new MedicalInstitution();
    assertThatThrownBy(() -> underTest.addNewMedicalInstitution(medicalInstitution))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Некорректные данные о медицинской организации.");
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
    given(medicalInstitutionRepository.findByNameMiAndNameSubject(
        medicalInstitution.getNameMi(),
        medicalInstitution.getNameSubject())).willReturn(Optional.of(medicalInstitution));
    assertThatThrownBy(() -> underTest.addNewMedicalInstitution(medicalInstitution))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(
            "Медицинская организация с таким id/именем/названием уже существует: "
                + medicalInstitution.getNameMi());
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
    given(medicalInstitutionRepository.findByNameMiAndNameSubject(
        medicalInstitution.getNameMi(),
        medicalInstitution.getNameSubject())).willReturn(Optional.empty());
    given(subjectService.findByNameSubject(medicalInstitution.getNameSubject())).willReturn(
        Optional.empty());
    assertThatThrownBy(() -> underTest.addNewMedicalInstitution(medicalInstitution))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(
            "Субъекта с таким id/именем/названием не существует: "
                + medicalInstitution.getNameSubject());
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
    given(medicalInstitutionRepository.existsById(medicalInstitution.getIdMi())).willReturn(true);
    underTest.updateMedicalInstitution(medicalInstitution);
    verify(medicalInstitutionRepository).save(medicalInstitution);
  }

  @Test
  void willThrowWhenUpdateMedicalInstitutionIsNull() {
    MedicalInstitution medicalInstitution = new MedicalInstitution();
    assertThatThrownBy(() -> underTest.updateMedicalInstitution(medicalInstitution))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Некорректные данные о медицинской организации.");
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
    given(medicalInstitutionRepository.existsById(medicalInstitution.getIdMi())).willReturn(false);
    assertThatThrownBy(() -> underTest.updateMedicalInstitution(medicalInstitution))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Нет медицинских организаций с таким id: " + medicalInstitution.getIdMi());
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
        .hasMessage("Нет медицинских организаций с таким id: " + id);
    verify(medicalInstitutionRepository, never()).deleteById(id);
  }

  @Test
  void willThrowWhenDeleteNullMedicalInstitutionById() {
    assertThatThrownBy(() -> underTest.deleteMedicalInstitutionById(null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректные данные о медицинской организации.");
    verify(medicalInstitutionRepository, never()).deleteById(anyLong());
  }


}