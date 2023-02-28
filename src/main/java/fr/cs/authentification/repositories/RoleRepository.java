package fr.cs.authentification.repositories;

import fr.cs.authentification.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
   Optional<Role>  findByRoleName(String roleName);
}
