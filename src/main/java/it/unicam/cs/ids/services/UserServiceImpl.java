package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.auth.services.AuthService;
import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.dtos.requests.user.config.DeleteUserRequest;
import it.unicam.cs.ids.dtos.requests.user.config.EditUserRequest;
import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.mappers.UserMapper;
import it.unicam.cs.ids.repositories.UserRepository;
import it.unicam.cs.ids.utils.InfrastructureTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthService authService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authService = authService;
    }

    @Override
    public User editUser(EditUserRequest request) {
        User authenticatedUser = authService.getAuthenticatedUser();
        User updatedUser = userMapper.updateUserFromRequest(authenticatedUser, request);

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(DeleteUserRequest request) {
        User authenticatedUser = authService.getAuthenticatedUser();
        PasswordEncoder passwordEncoder = authService.getPasswordEncoder();
        InfrastructureTools.validateUserDelete(passwordEncoder, authenticatedUser, request);
        userRepository.delete(authenticatedUser);

    }
}
