package it.unicam.cs.ids.api.auth.user;


import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    public static AppUserPrincipal fromUser(User user) {
        return new AppUserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getHashedPassword(),
                null, // no VAT for user
                Collections.singletonList(new SimpleGrantedAuthority(PlatformRoles.SHOPPER.getRole())),
                user.isEmailVerified(),
                false
        );
    }

    // Factory method for Company
    public static AppUserPrincipal fromCompany(Company company) {
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

    //TODO: ADD curatore...
    //TODO: ADD ADMIN

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

