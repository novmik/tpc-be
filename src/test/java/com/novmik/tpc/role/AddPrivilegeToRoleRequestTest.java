package com.novmik.tpc.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddPrivilegeToRoleRequestTest {

  AddPrivilegeToRoleRequest underTest;

  @BeforeEach
  void setUp() {
    underTest = new AddPrivilegeToRoleRequest("Test role Name", "Test privilege Name");
  }

  @Test
  void getRoleName() {
    assertThat(underTest.getRoleName()).isEqualTo("Test role Name");
  }

  @Test
  void getPrivilegeName() {
    assertThat(underTest.getPrivilegeName()).isEqualTo("Test privilege Name");
  }

  @Test
  void setRoleName() {
    underTest.setRoleName("Set Test Role Name");
    assertThat(underTest.getRoleName()).isEqualTo("Set Test Role Name");
  }

  @Test
  void setPrivilegeName() {
    underTest.setPrivilegeName("Set Test Privilege Name");
    assertThat(underTest.getPrivilegeName()).isEqualTo("Set Test Privilege Name");
  }

  @Test
  void testEquals() {
    AddPrivilegeToRoleRequest test2 = new AddPrivilegeToRoleRequest("Test role Name", "Test privilege Name");
    assertThat(underTest.equals(test2)).isTrue();
  }

  @Test
  void canEqual() {
    AddPrivilegeToRoleRequest test5 = underTest;
    assertThat(underTest.canEqual(test5)).isTrue();
  }

  @Test
  void testHashCode() {
    AddPrivilegeToRoleRequest test3 = new AddPrivilegeToRoleRequest("Test role Name", "Test privilege Name");
    assertThat(underTest.hashCode()).isEqualTo(test3.hashCode());
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo("AddPrivilegeToRoleRequest(roleName=Test role Name, privilegeName=Test privilege Name)");
  }
}