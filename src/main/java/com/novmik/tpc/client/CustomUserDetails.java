package com.novmik.tpc.client;

import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.role.Role;
import java.io.Serial;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@link UserDetails}.
 */
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.UselessOverridingMethod"})
public class CustomUserDetails extends Client implements UserDetails {

  @Serial
  private static final long serialVersionUID = 862301076335796787L;

  /**
   * Ctor.
   *
   * @param client {@link Client}
   */
  public CustomUserDetails(final Client client) {
    super(client);
  }

  /**
   * Получение прав доступа.
   *
   * @param roles список {@link Role}
   * @return список {@link SimpleGrantedAuthority}
   */
  private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
    return Stream.concat(
            roles.stream().map(Role::getName),
            roles.stream().flatMap(role -> role.getPrivileges().stream().map(Privilege::getName))
        ).distinct()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getAuthorities(super.getRoles());
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public String getUsername() {
    return super.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return super.isNotLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return super.isEnabled();
  }
}
