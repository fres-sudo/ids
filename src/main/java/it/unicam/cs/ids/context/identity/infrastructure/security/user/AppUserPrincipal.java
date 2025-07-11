package it.unicam.cs.ids.context.identity.infrastructure.security.user;


import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter @AllArgsConstructor
public class AppUserPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String vat;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean emailVerified;
    private final boolean isCompany;


    // Factory method for User
    public static AppUserPrincipal fromUser(@NonNull User user) {
        PlatformRoles role = user.getRole();
        String authority = role.getRole();
        return new AppUserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getHashedPassword(),
                null, // no VAT for user
                Collections.singletonList(new SimpleGrantedAuthority(authority)),
                user.isEmailVerified(),
                false // not a company
        );
    }

    // Factory method for Company
    public static AppUserPrincipal fromCompany(@NonNull Company company) {
        return new AppUserPrincipal(
                company.getId(),
                company.getEmail(),
                company.getHashedPassword(),
                company.getVat(),
                Collections.singletonList(new SimpleGrantedAuthority(PlatformRoles.COMPANY.getRole())),
                company.isEmailVerified(),
                true
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return emailVerified;
    }
}

