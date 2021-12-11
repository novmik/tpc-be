package com.novmik.tpc.auth;

import com.novmik.tpc.role.Role;
import java.util.Collection;
import lombok.Data;

/**
 * Запрос на регистрацию клиента.
 */
@Data
public class RegistrationRequest {

  /**
   * Email.
   */
  private String email;
  /**
   * Пароль.
   */
  private String password;
  /**
   * Имя.
   */
  private String firstName;
  /**
   * Фамилия.
   */
  private String lastName;
  /**
   * Роли(с наименованиями полномочия).
   */
  private Collection<Role> roles;
}
