package com.novmik.tpc.privilege;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class PrivilegeServiceTest {

  @Mock
  private PrivilegeRepository privilegeRepo;
  private PrivilegeService underTest;

  @BeforeEach
  void setUp() {
    underTest = new PrivilegeService(privilegeRepo);
  }

  @Test
  void canGetAllPrivilege() {
    underTest.getAllPrivilege();
    verify(privilegeRepo).findAll();
  }

  @Test
  void findByPrivilegeName() {
    String privilegeName = "Test Privilege Name";
    underTest.findByPrivilegeName(privilegeName);
    verify(privilegeRepo).findByName(privilegeName);
  }

  @Test
  void existById() {
    long idPrivilege = 50L;
    underTest.existById(idPrivilege);
    verify(privilegeRepo).existsById(idPrivilege);
  }

  @Test
  void canSave() {
    Privilege privilege = new Privilege(170L, "Test");
    underTest.save(privilege);
    verify(privilegeRepo).save(privilege);
  }

  @Test
  void canAddNewPrivilege() {
    Privilege privilege = new Privilege(170L, "Test");
    when(underTest.findByPrivilegeName(anyString())).thenReturn(Optional.empty());
    underTest.addNewPrivilege(privilege);
    ArgumentCaptor<Privilege> privilegeArgumentCaptor = ArgumentCaptor.forClass(Privilege.class);
    verify(privilegeRepo).save(privilegeArgumentCaptor.capture());
    Privilege capturedPrivilege = privilegeArgumentCaptor.getValue();
    assertThat(capturedPrivilege).isEqualTo(privilege);
  }

  @Test
  void willThrowWhenAddNewPrivilegeWhenIncorrect() {
    Privilege privilege = new Privilege();
    assertThatThrownBy(() -> underTest.addNewPrivilege(privilege))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректные данные о привилегии.");
    verify(privilegeRepo, never()).save(privilege);
  }

  @Test
  void willThrowWhenAddNewPrivilegeWhenAlreadyExist() {
    Privilege privilege = new Privilege(170L, "Test");
    when(underTest.findByPrivilegeName(anyString())).thenReturn(Optional.of(privilege));
    assertThatThrownBy(() -> underTest.addNewPrivilege(privilege))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Привилегия уже существует: " + privilege.getName());
    verify(privilegeRepo, never()).save(privilege);
  }

  @Test
  void canDeletePrivilegeById() {
    long idPrivilege = 50L;
    when(underTest.existById(idPrivilege)).thenReturn(true);
    underTest.deletePrivilegeById(idPrivilege);
    verify(privilegeRepo).deleteById(idPrivilege);
  }

  @Test
  void willThrowWhenDeletePrivilegeByIdIsNotCorrect() {
    long idPrivilege = 0;
    assertThatThrownBy(() -> underTest.deletePrivilegeById(idPrivilege))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректные данные о привилегии: " + idPrivilege);
    verify(privilegeRepo, never()).deleteById(idPrivilege);
  }

  @Test
  void willThrowWhenDeletePrivilegeByIdIsNotExist() {
    long idPrivilege = 300L;
    when(underTest.existById(idPrivilege)).thenReturn(false);
    assertThatThrownBy(() -> underTest.deletePrivilegeById(idPrivilege))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Привилегии с таким id/названием не существует: " + idPrivilege);
    verify(privilegeRepo, never()).deleteById(idPrivilege);
  }
}