package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.auth.services.AuthService;
import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.dtos.UserDTO;
import it.unicam.cs.ids.dtos.requests.user.config.EditUserRequest;
import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.mappers.UserMapper;
import it.unicam.cs.ids.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;


    @Override
    public UserDTO editUser(EditUserRequest request) {
        User authenticatedUser = authService.getAuthenticatedUser();
        User updatedUser = userMapper.updateUserFromRequest(authenticatedUser, request);
        return userMapper.toDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser() {
        User authenticatedUser = authService.getAuthenticatedUser();
        userRepository.deleteById(authenticatedUser.getId());
    }
}
