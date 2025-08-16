package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.services.UserService;
import lombok.RequiredArgsConstructor;
import it.unicam.cs.ids.dto.UserDTO;
import it.unicam.cs.ids.web.requests.user.EditUserRequest;
import it.unicam.cs.ids.model.User;
import it.unicam.cs.ids.mappers.UserMapper;
import it.unicam.cs.ids.repositories.UserRepository;
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
