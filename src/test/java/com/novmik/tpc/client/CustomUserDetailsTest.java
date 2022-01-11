package com.novmik.tpc.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.role.Role;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomUserDetailsTest {

  CustomUserDetails underTest;

  @BeforeEach
  void setUp() {
    Privilege privilege = new Privilege(20L, "test Name");
    Role role = new Role(40L, "name Role", List.of(privilege));
    Client client = new Client(30L, "email@email.ru", "pass", "T", "E", null, null, true, true,
        List.of(role));
    underTest = new CustomUserDetails(client);
  }

  @Test
  void getAuthorities() {
    assertThat(underTest.getAuthorities()).isNotNull();
  }

  @Test
  void getPassword() {
    assertThat(underTest.getPassword()).isEqualTo("pass");
  }

  @Test
  void getUsername() {
    assertThat(underTest.getUsername()).isEqualTo("email@email.ru");
  }

  @Test
  void isAccountNonExpired() {
    assertThat(underTest.isAccountNonExpired()).isTrue();
  }

  @Test
  void isAccountNonLocked() {
    assertThat(underTest.isAccountNonLocked()).isTrue();
  }

  @Test
  void isCredentialsNonExpired() {
    assertThat(underTest.isCredentialsNonExpired()).isTrue();
  }

  @Test
  void isEnabled() {
    assertThat(underTest.isEnabled()).isTrue();
  }
}