package dev.onurcakir.ecommerce.repository;

import dev.onurcakir.ecommerce.model.Role;
import dev.onurcakir.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
    Boolean existsByName(String name);

    Page<Role> findAll(Pageable pageable);

}