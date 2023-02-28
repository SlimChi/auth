package fr.cs.authentification.service;



import fr.cs.authentification.dto.AuthenticationRequest;
import fr.cs.authentification.dto.AuthenticationResponse;
import fr.cs.authentification.dto.UserDto;
import fr.cs.authentification.entities.Role;
import fr.cs.authentification.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {


    User addNewUser(User user);

    Integer save(UserDto dto);

    @Transactional
    List<UserDto> findAll();

    UserDto findById(Integer id);

    void delete(Integer id);


    List<User> listUsers();

    Role addRoleToUser(String roleName);

    @Transactional
    AuthenticationResponse register(UserDto dto);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Integer update(UserDto userDto);
}
