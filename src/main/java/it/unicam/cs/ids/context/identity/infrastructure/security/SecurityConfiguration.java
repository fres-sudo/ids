package it.unicam.cs.ids.context.identity.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.infrastructure.security.jwt.JwtAuthEntryPoint;
import it.unicam.cs.ids.context.identity.infrastructure.security.jwt.JwtAuthenticationFilter;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.UserDetailsServiceImpl;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up Spring Security
 * This class configures the security filter chain & authentication provider
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@AllArgsConstructor
public class SecurityConfiguration {
    /** User details service for loading user-specific data */
    private final UserDetailsServiceImpl userDetailsService;
    /** JWT authentication entry point for handling unauthorized access */
    private final JwtAuthEntryPoint authEntryPoint;
    /** JWT authentication filter for processing JWT tokens */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * This method disables CSRF protection (typically for stateless APIs),
     * sets up exception handling to use a custom authentication entry point,
     * configures the session management to be stateless (no HTTP session),
     * defines authorization rules to permit all requests to {@code /api/auth/**}
     * and require authentication for any other requests,
     * sets the authentication provider,
     * and adds a JWT authentication filter before the standard
     * UsernamePasswordAuthenticationFilter in the filter chain, in this case <b> username = email (unique)</b>
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if any configuration error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/search/**").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority(
                                PlatformRoles.BUYER.name(),
                                PlatformRoles.CERTIFIER.name(),
                                PlatformRoles.ADMIN.name()
                        )
                        .requestMatchers("/purchase/**").hasAnyAuthority(
                                PlatformRoles.BUYER.name(),
                                CompanyRoles.PRODUCER.name(),
                                CompanyRoles.TRANSFORMER.name(),
                                CompanyRoles.DISTRIBUTOR.name(),
                                PlatformRoles.CERTIFIER.name(),
                                PlatformRoles.ADMIN.name()
                        )
                        .requestMatchers("/products/**").hasAnyAuthority(
                                CompanyRoles.PRODUCER.name(),
                                CompanyRoles.TRANSFORMER.name(),
                                CompanyRoles.DISTRIBUTOR.name()
                        )
                        .requestMatchers("/bundles/**").hasAnyAuthority(
                                CompanyRoles.DISTRIBUTOR.name()
                        )
                        .requestMatchers("/companies/**").hasAnyAuthority(
                                CompanyRoles.PRODUCER.name(),
                                CompanyRoles.TRANSFORMER.name(),
                                CompanyRoles.DISTRIBUTOR.name()
                        )
                        .requestMatchers("/certifier/**").hasAnyAuthority(
                                PlatformRoles.CERTIFIER.name()
                        )
                        .requestMatchers("/admin/**").hasAuthority(
                                PlatformRoles.ADMIN.name()
                        )
                        .requestMatchers("/events/**").hasAnyAuthority(
                                PlatformRoles.ANIMATOR.name()
                        )
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a DaoAuthenticationProvider bean.
     * <p>
     * Configures the authentication provider to use a custom UserDetailsService
     * and a BCrypt password encoder.
     * The setting {@code setHideUserNotFoundExceptions(false)} enables
     * exceptions for user-not-found scenarios to be visible (useful for debugging).
     *
     * @return a configured DaoAuthenticationProvider instance
     */
    @Bean @SuppressWarnings("deprecation")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false); // Important for debugging
        return provider;
    }

    /**
     * Provides a PasswordEncoder bean that uses BCrypt hashing function.
     *
     * @return a BCryptPasswordEncoder instance for hashing passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the AuthenticationManager bean.
     * <p>
     * This method obtains the AuthenticationManager from the
     * AuthenticationConfiguration which holds the global authentication setup.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the AuthenticationManager instance
     * @throws Exception if unable to retrieve the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}