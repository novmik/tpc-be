package com.novmik.tpc.role;

import com.novmik.tpc.privilege.Privilege;
import java.util.Collection;
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
 * Роль entity class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "role")
@SuppressWarnings("PMD.ShortClassName")
public class Role {

  /**
   * id роли.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long idRole;
  /**
   * Наименование роли.
   */
  @Column(name = "name")
  private String name;
  /**
   * Список полномочий роли.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_privilege",
      joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
  private Collection<Privilege> privileges;
}
