package com.novmik.tpc.client;

import com.novmik.tpc.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/*
Таблица: Пользователи
Колонки: Емайл адрес, Пароль, Имя, Фамилия, Включен, Не заблокирован
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "last_login_date")
    private Date lastLoginDate;
    @Column(name = "join_date")
    private Date joinDate;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "is_not_locked")
    private boolean isNotLocked;
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

    public Client(String email, String password, String firstName, String lastName, boolean isEnabled, boolean isNotLocked) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEnabled = isEnabled;
        this.isNotLocked = isNotLocked;
    }

    public Client(Client client) {
        id = client.getId();
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
