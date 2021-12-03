package com.novmik.tpc.drg;

import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_EXISTS;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_NOT_CORRECT;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_NOT_EXISTS;
import static com.novmik.tpc.drg.DiagnosisRelatedGroupsConstant.DRG_NOT_EXISTS_BY_NAME_DRG;
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
class DiagnosisRelatedGroupsServiceTest {

  @Mock
  private DiagnosisRelatedGroupsRepository drgRepository;
  private DiagnosisRelatedGroupsService underTest;

  @BeforeEach
  void setUp() {
    underTest = new DiagnosisRelatedGroupsService(drgRepository);
  }

  @Test
  void canGetBooleanWhenDrgExistsById() {
    long id = 10;
    underTest.existsById(id);
    verify(drgRepository).existsById(id);
  }

  @Test
  void canGetTrueWhenDrgExistById() {
    long id = 10;
    given(drgRepository.existsById(id)).willReturn(true);
    assertThat(underTest.existsById(id)).isTrue();
  }

  @Test
  void canGetFalseWhenDrgExistById() {
    long id = 10;
    given(drgRepository.existsById(id)).willReturn(false);
    assertThat(underTest.existsById(id)).isFalse();
  }

  @Test
  void canFindByDrg() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
        1L,
        "Test NumberDrg",
        "Test NominationDrg",
        2F,
        3F
    );
    given(drgRepository.findByNumberDrg(diagnosisRelatedGroups.getNumberDrg())).willReturn(
        Optional.of(diagnosisRelatedGroups));
    underTest.byNumberDrg(diagnosisRelatedGroups.getNumberDrg());
    verify(drgRepository).findByNumberDrg(diagnosisRelatedGroups.getNumberDrg());
  }

  @Test
  void willThrowWhenCanNotFindByDrg() {
    String numberDrg = "Test NumberDrg";
    given(drgRepository.findByNumberDrg(numberDrg)).willReturn(Optional.empty());
    assertThatThrownBy(() -> underTest.byNumberDrg(numberDrg))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(DRG_NOT_EXISTS_BY_NAME_DRG + numberDrg);
  }

  @Test
  void canAddNewDrg() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
        1L,
        "Test NumberDrg",
        "Test NominationDrg",
        2F,
        3F
    );
    underTest.addNewDrg(diagnosisRelatedGroups);
    ArgumentCaptor<DiagnosisRelatedGroups> diagnosisRelatedGroupsArgumentCaptor =
        ArgumentCaptor.forClass(DiagnosisRelatedGroups.class);
    verify(drgRepository).save(diagnosisRelatedGroupsArgumentCaptor.capture());
    DiagnosisRelatedGroups capturedDrg = diagnosisRelatedGroupsArgumentCaptor.getValue();
    assertThat(capturedDrg).isEqualTo(diagnosisRelatedGroups);
  }

  @Test
  void willThrowWhenAddNewNullDrg() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups();
    assertThatThrownBy(() -> underTest.addNewDrg(diagnosisRelatedGroups))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(DRG_NOT_CORRECT);
    verify(drgRepository, never()).save(diagnosisRelatedGroups);
  }

  @Test
  void willThrowWhenAddDrgWhichExists() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
        1L,
        "Test NumberDrg",
        "Test NominationDrg",
        2F,
        3F
    );
    given(drgRepository.findByNumberDrg(diagnosisRelatedGroups.getNumberDrg())).willReturn(
        Optional.of(diagnosisRelatedGroups));
    assertThatThrownBy(() -> underTest.addNewDrg(diagnosisRelatedGroups))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(DRG_EXISTS + diagnosisRelatedGroups.getNumberDrg());
    verify(drgRepository, never()).save(diagnosisRelatedGroups);
  }

  @Test
  void canUpdateDrg() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
        1L,
        "Test NumberDrg",
        "Test NominationDrg",
        2F,
        3F
    );
    given(drgRepository.existsById(diagnosisRelatedGroups.getId())).willReturn(true);
    underTest.updateDrg(diagnosisRelatedGroups);
    verify(drgRepository).save(diagnosisRelatedGroups);
  }

  @Test
  void willThrowWhenUpdateDrgIsNull() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups();
    assertThatThrownBy(() -> underTest.updateDrg(diagnosisRelatedGroups))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining(DRG_NOT_CORRECT);
    verify(drgRepository, never()).save(diagnosisRelatedGroups);
  }

  @Test
  void willThrowWhenUpdateDrgNotExists() {
    DiagnosisRelatedGroups diagnosisRelatedGroups = new DiagnosisRelatedGroups(
        1L,
        "Test NumberDrg",
        "Test NominationDrg",
        2F,
        3F
    );
    given(drgRepository.existsById(diagnosisRelatedGroups.getId())).willReturn(false);
    assertThatThrownBy(() -> underTest.updateDrg(diagnosisRelatedGroups))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(DRG_NOT_EXISTS + diagnosisRelatedGroups.getId());
    verify(drgRepository, never()).save(diagnosisRelatedGroups);
  }

  @Test
  void canDeleteDrgById() {
    long id = 10;
    given(drgRepository.existsById(id)).willReturn(true);
    underTest.deleteDrgById(id);
    verify(drgRepository).deleteById(id);
  }

  @Test
  void willThrowWhenDeleteDrgByIdWhichNotExists() {
    long id = 10;
    given(drgRepository.existsById(id)).willReturn(false);
    assertThatThrownBy(() -> underTest.deleteDrgById(id))
        .isInstanceOf(NotFoundException.class)
        .hasMessage(DRG_NOT_EXISTS + id);
    verify(drgRepository, never()).deleteById(anyLong());
  }

  @Test
  void willThrowWhenDeleteNullDrgById() {
    assertThatThrownBy(() -> underTest.deleteDrgById(null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage(DRG_NOT_CORRECT);
    verify(drgRepository, never()).deleteById(anyLong());
  }
}