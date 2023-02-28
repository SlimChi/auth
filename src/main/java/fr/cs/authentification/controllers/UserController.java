package fr.cs.authentification.controllers;

import fr.cs.authentification.dto.UserDto;
import fr.cs.authentification.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {

  private final UserService service;

  @PostMapping("/")
  public ResponseEntity<Integer> save(
      @RequestBody UserDto userDto
  ) {
    return ResponseEntity.ok(service.update(userDto));
  }

  @GetMapping("/")
  public ResponseEntity<List<UserDto>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{user-id}")
  public ResponseEntity<UserDto> findById(
      @PathVariable("user-id") Integer userId
  ) {
    return ResponseEntity.ok(service.findById(userId));
  }


  @DeleteMapping("/{user-id}")
  public ResponseEntity<Void> delete(
      @PathVariable("user-id") Integer userId
  ) {
    service.delete(userId);
    return ResponseEntity.accepted().build();
  }

}
