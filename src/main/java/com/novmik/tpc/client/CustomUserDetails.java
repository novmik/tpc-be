package com.novmik.tpc.client;

import com.novmik.tpc.privilege.Privilege;
import com.novmik.tpc.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CustomUserDetails extends Client implements UserDetails {

    public CustomUserDetails(final Client client) {
        super(client);
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

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return Stream.concat(
                roles.stream().map(Role::getName), roles.stream()
                        .flatMap(role -> role.getPrivileges().stream().map(Privilege::getName)))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
