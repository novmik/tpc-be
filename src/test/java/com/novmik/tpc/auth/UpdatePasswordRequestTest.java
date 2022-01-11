package com.novmik.tpc.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdatePasswordRequestTest {

  UpdatePasswordRequest underTest;

  @BeforeEach
  void setUp() {
    underTest = new UpdatePasswordRequest("oldPass", "newPass");
  }

  @Test
  void getOldPassword() {
    assertThat(underTest.getOldPassword()).isEqualTo("oldPass");
  }

  @Test
  void getNewPassword() {
    assertThat(underTest.getNewPassword()).isEqualTo("newPass");
  }

  @Test
  void setOldPassword() {
    underTest.setOldPassword("Set Test OldPass");
    assertThat(underTest.getOldPassword()).isEqualTo("Set Test OldPass");
  }

  @Test
  void setNewPassword() {
    underTest.setNewPassword("Set Test NewPass");
    assertThat(underTest.getNewPassword()).isEqualTo("Set Test NewPass");
  }

  @Test
  void testEquals() {
    UpdatePasswordRequest test1 = new UpdatePasswordRequest("oldPass", "newPass");
    assertThat(underTest.equals(test1)).isTrue();
  }

  @Test
  void canEqual() {
    UpdatePasswordRequest test1 = underTest;
    assertThat(underTest.canEqual(test1)).isTrue();
  }

  @Test
  void testHashCode() {
    UpdatePasswordRequest test11 = new UpdatePasswordRequest("oldPass", "newPass");
    assertThat(underTest.hashCode()).isEqualTo(test11.hashCode());
  }

  @Test
  void testToString() {
    assertThat(underTest.toString()).isEqualTo("UpdatePasswordRequest(oldPassword=oldPass, newPassword=newPass)");
  }
}