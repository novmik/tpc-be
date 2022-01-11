package com.novmik.tpc.privilege;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrivilegeTest {

  Privilege underTest;

  @BeforeEach
  void setUp() {
    underTest = new Privilege(90L, "Name Privilege");
  }

  @Test
  void getIdPrivilege() {
    assertThat(underTest.getIdPrivilege()).isEqualTo(90L);
  }

  @Test
  void getName() {
    assertThat(underTest.getName()).isEqualTo("Name Privilege");
  }

  @Test
  void setIdPrivilege() {
    underTest.setIdPrivilege(30L);
    assertThat(underTest.getIdPrivilege()).isEqualTo(30L);
  }

  @Test
  void setName() {
    underTest.setName("Set Test Name Privilege");
    assertThat(underTest.getName()).isEqualTo("Set Test Name Privilege");
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo("Privilege(idPrivilege=90, name=Name Privilege)");
  }
}