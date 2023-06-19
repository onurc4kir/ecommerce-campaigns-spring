package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.exception.model.BadRequestException;
import dev.onurcakir.ecommerce.exception.model.NotFoundException;
import dev.onurcakir.ecommerce.model.Role;
import dev.onurcakir.ecommerce.payload.request.RoleCreatePayload;
import dev.onurcakir.ecommerce.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class RoleController {

    final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @GetMapping("")
    public ResponseEntity<Page<Role>> getAllRolesPaginated(@RequestParam int page, @RequestParam int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Role> roles = roleRepository.findAll(pageable);
        return ResponseEntity.ok(roles);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("Role not found!");
        }
        final Role role = roleRepository.findById(id).get();
        return ResponseEntity.ok(role);
    }

    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody RoleCreatePayload payload) {
        if (roleRepository.existsByName(payload.getName())) {
            throw new BadRequestException("Role is already exists!");
        }
        final Role savedRole = roleRepository.save(new Role(payload.getName()));
        return ResponseEntity.ok(savedRole);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Role role) {
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("Role not found!");
        }
        final Role savedRole = roleRepository.save(role);
        return ResponseEntity.ok(savedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("Role not found!");
        }
        roleRepository.deleteById(id);
        return ResponseEntity.ok("Role deleted successfully!");
    }
}
