package fr.cs.authentification.dto;


import fr.cs.authentification.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {


  private Integer id;

  @NotNull(message = "Le prenom ne doit pas etre vide")
  @NotEmpty(message = "Le prenom ne doit pas etre vide")
  @NotBlank(message = "Le prenom ne doit pas etre vide")
  private String firstname;

  @NotNull(message = "Le nom ne doit pas etre vide")
  @NotEmpty(message = "Le nom ne doit pas etre vide")
  @NotBlank(message = "Le nom ne doit pas etre vide")
  private String lastname;

  @NotNull(message = "L'email ne doit pas etre vide")
  @NotEmpty(message = "L'email ne doit pas etre vide")
  @NotBlank(message = "L'email ne doit pas etre vide")
  @Email(message = "L'email n'est conforme")
  private String email;

  @NotNull(message = "Le mot de passe ne doit pas etre vide")
  @NotEmpty(message = "Le mot de passe ne doit pas etre vide")
  @NotBlank(message = "Le mot de passe ne doit pas etre vide")
  @Size(min = 8, max = 16, message = "Le mot de passe doit etre entre 8 et 16 caracteres")
  private String password;


  public static UserDto fromEntity(User user) {
    // null check
    return UserDto.builder()
        .id(user.getId())
        .firstname(user.getFirstName())
        .lastname(user.getLastName())
        .email(user.getEmail())
        .build();
  }

  public static User toEntity(UserDto user) {
    // null check
    return User.builder()
        .id(user.getId())
        .firstName(user.getFirstname())
        .lastName(user.getLastname())
        .email(user.getEmail())
        .password(user.getPassword())
        .build();
  }

}
