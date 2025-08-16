package it.unicam.cs.ids.security.user;


import it.unicam.cs.ids.models.User;
import it.unicam.cs.ids.models.Company;
import it.unicam.cs.ids.shared.kernel.enums.PlatformRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the authenticated user in the application.
 * Implements Spring Security's UserDetails interface to provide user information
 * such as username, password, roles, and account status.
 */
@Getter @AllArgsConstructor
public class AppUserPrincipal implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final String vat;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean emailVerified;
    private final boolean isCompany;

    /**
     * Factory method to create an AppUserPrincipal from a User entity.
     * @param user the User entity to convert
     * @return an AppUserPrincipal instance representing the user
     */
    public static AppUserPrincipal fromUser(@NonNull User user) {
        PlatformRoles role = user.getRole();
        String authority = role.name();
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

    /**
     * Factory method to create an AppUserPrincipal from a Company entity.
     * @param company the Company entity to convert
     * @return an AppUserPrincipal instance representing the company
     */
    public static AppUserPrincipal fromCompany(@NonNull Company company) {
        String authority = company.getRole().toString();
        return new AppUserPrincipal(
                company.getId(),
                company.getEmail(),
                company.getHashedPassword(),
                company.getVat(),
                Collections.singletonList(new SimpleGrantedAuthority(authority)),
                company.isEmailVerified(),
                true
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return emailVerified;
    }
}

