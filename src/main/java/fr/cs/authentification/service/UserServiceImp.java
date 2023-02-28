package fr.cs.authentification.service;


import fr.cs.authentification.config.JwtUtils;
import fr.cs.authentification.dto.AuthenticationRequest;
import fr.cs.authentification.dto.AuthenticationResponse;
import fr.cs.authentification.dto.UserDto;
import fr.cs.authentification.entities.Role;
import fr.cs.authentification.entities.User;
import fr.cs.authentification.repositories.RoleRepository;
import fr.cs.authentification.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor

public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ROLE_USER = "ROLE_USER";
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;




    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public Integer save(UserDto dto) {
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check before delete
        userRepository.deleteById(id);
    }


    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Role addRoleToUser(String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElse(null);
        if (role == null) {
            return roleRepository.save(
                    Role.builder()
                            .roleName(roleName)
                            .build()
            );
        }
        return role;
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(
                addRoleToUser(ROLE_USER)
        );
        var savedUser = userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstName() + " " + savedUser.getLastName());
        String token = jwtUtils.generateToken((UserDetails) savedUser, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final User user = userRepository.findByEmail(request.getEmail()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstName() + " " + user.getLastName());
        final String token = jwtUtils.generateToken((UserDetails) user, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public Integer update(UserDto userDto) {
        User user = UserDto.toEntity(userDto);
        return userRepository.save(user).getId();
    }

}
