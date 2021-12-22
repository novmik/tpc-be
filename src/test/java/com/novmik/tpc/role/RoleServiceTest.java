package com.novmik.tpc.role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.privilege.PrivilegeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

  @Mock
  private RoleRepository roleRepository;
  @Mock
  private PrivilegeService privilegeService;
  private RoleService underTest;

  @BeforeEach
  void setUp() {
    underTest = new RoleService(roleRepository, privilegeService);
  }

  @Test
  void canGetAllRoles() {
    underTest.getAllRoles();
    verify(roleRepository).findAll();
  }

  @Test
  void canFindRoleByName() {
    Role role = new Role(100L, "Test Role Name", Collections.emptyList());
    when(roleRepository.findRoleByName(role.getName())).thenReturn(Optional.of(role));
    assertThat(underTest.findRoleByName(role.getName())).isPresent();
  }

  @Test
  void willThrowWhenFindRoleByName() {
    String roleName = "Test Role Name";
    when(roleRepository.findRoleByName(roleName)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> underTest.findRoleByName(roleName))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Роль не найдена: " + roleName);
  }

  @Test
  void canAddNewRole() {
    Role role = new Role(100L, "Test Role Name", Collections.emptyList());
    when(roleRepository.findRoleByName(role.getName())).thenReturn(Optional.empty());
    underTest.addNewRole(role);
    ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
    verify(roleRepository).save(roleArgumentCaptor.capture());
    Role capturedRole = roleArgumentCaptor.getValue();
    assertThat(capturedRole).isEqualTo(role);
  }

  @Test
  void willThrowWhenAddNewRoleWhenRoleExist() {
    Role role = new Role(100L, "Test Role Name", Collections.emptyList());
    when(roleRepository.findRoleByName(role.getName())).thenReturn(Optional.of(role));
    assertThatThrownBy(() -> underTest.addNewRole(role))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Роль с таким id/названием уже существует: " + role.getName());
    verify(roleRepository, never()).save(role);
  }

  @Test
  void willThrowWhenAddNewRoleWhenRoleIncorrect() {
    Role role = new Role();
    assertThatThrownBy(() -> underTest.addNewRole(role))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Некорректные данные о роли" + role);
    verify(roleRepository, never()).save(role);
  }

  @Test
  void canAddPrivilegeToRole() {
    Collection<Privilege> privilegeCollection = new ArrayList<>();
    Role role = new Role(100L, "Test Role Name", privilegeCollection);
    Privilege privilege = new Privilege(170L, "Test Privilege Name");
    when(roleRepository.findRoleByName(anyString())).thenReturn(Optional.of(role));
    when(privilegeService.findByPrivilegeName(anyString())).thenReturn(Optional.of(privilege));
    underTest.addPrivilegeToRole(role.getName(), privilege.getName());
  }

  @Test
  void willThrowWhenAddPrivilegeToRole() {
    Collection<Privilege> privilegeCollection = new ArrayList<>();
    Role role = new Role(100L, "Test Role Name", privilegeCollection);
    Privilege privilege = new Privilege(170L, "Test Privilege Name");
    when(roleRepository.findRoleByName(anyString())).thenReturn(Optional.empty());
    when(privilegeService.findByPrivilegeName(anyString())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> underTest.addPrivilegeToRole(role.getName(), privilege.getName()))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Роль/полномочия не найдены.");
  }
}