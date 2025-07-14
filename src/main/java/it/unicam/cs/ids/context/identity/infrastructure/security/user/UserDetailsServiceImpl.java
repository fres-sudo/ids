package it.unicam.cs.ids.context.identity.infrastructure.security.user;


import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserDetailsService that loads user details by username.
 * It checks repositories to find the users.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {
    /** Repository for accessing user data */
    private final UserRepository userRepository;
    /** Repository for accessing company data */
    private final CompanyRepository companyRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            return AppUserPrincipal.fromUser(userOpt.get());
        }

        var companyOpt = companyRepository.findByEmail(username);
        if (companyOpt.isPresent()) {
            return AppUserPrincipal.fromCompany(companyOpt.get());
        }

        throw new UsernameNotFoundException("User or company not found with email: " + username);
    }
}