package it.unicam.cs.ids.context.identity.application.services;

import lombok.RequiredArgsConstructor;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.EditUserRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

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
