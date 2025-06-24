package it.unicam.cs.ids.api.config.data;

import it.unicam.cs.ids.entities.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean emailVerified;

    public UserPrincipal(Long id, String email, String password,
                         Collection<? extends GrantedAuthority> authorities,
                         boolean emailVerified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.emailVerified = emailVerified;
    }

    public static UserPrincipal create(User user) {
        // For basic implementation, all users get USER role
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getHashedPassword(),
                authorities,
                user.isEmailVerified()
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return emailVerified; // Only allow login if email is verified
    }
}