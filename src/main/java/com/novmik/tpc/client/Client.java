package com.novmik.tpc.client;

import com.novmik.tpc.role.Role;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Клиент entity class.
 * Клиент=пользователь=user
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "client")
@SuppressWarnings("PMD.CommentSize")
public class Client {

  /**
   * id клиента.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idClient;
  /**
   * Email.
   */
  @Column(name = "email")
  private String email;
  /**
   * Пароль.
   */
  @Column(name = "password")
  private String password;
  /**
   * Имя.
   */
  @Column(name = "first_name")
  private String firstName;
  /**
   * Фамилия.
   */
  @Column(name = "last_name")
  private String lastName;
  /**
   * Дата последнего логина.
   */
  @Column(name = "last_login_date")
  private Date lastLoginDate;
  /**
   * Дата логина.
   */
  @Column(name = "join_date")
  private Date joinDate;
  /**
   * Включение.
   */
  @Column(name = "is_enabled")
  private boolean isEnabled;
  /**
   * Блокировка.
   */
  @Column(name = "is_not_locked")
  private boolean isNotLocked;
  /**
   * Список ролей.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "client_role",
      joinColumns = @JoinColumn(
          name = "client_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"
      )
  )
  private Collection<Role> roles;

  /**
   * Ctor без id.
   *
   * @param email email
   * @param password пароль
   * @param firstName имя
   * @param lastName фамилия
   * @param isEnabled включение
   * @param isNotLocked блокировка
   */
  public Client(final String email,
      final String password,
      final String firstName,
      final String lastName,
      final boolean isEnabled,
      final boolean isNotLocked) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.isEnabled = isEnabled;
    this.isNotLocked = isNotLocked;
  }

  /**
   * Ctor.
   *
   * @param client {@link Client}
   */
  public Client(final Client client) {
    idClient = client.getIdClient();
    email = client.getEmail();
    password = client.getPassword();
    firstName = client.getFirstName();
    lastName = client.getLastName();
    lastLoginDate = client.getLastLoginDate();
    joinDate = client.getJoinDate();
    isEnabled = client.isEnabled;
    isNotLocked = client.isNotLocked;
    roles = client.getRoles();
  }
}
