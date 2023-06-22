package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.exception.model.NotFoundException;
import dev.onurcakir.ecommerce.model.Profession;
import dev.onurcakir.ecommerce.model.User;
import dev.onurcakir.ecommerce.payload.request.UpdateProfilePayload;
import dev.onurcakir.ecommerce.payload.response.MessageResponse;
import dev.onurcakir.ecommerce.repository.ProfessionRepository;
import dev.onurcakir.ecommerce.repository.UserRepository;
import dev.onurcakir.ecommerce.security.service.UserDetailsImpl;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    final UserRepository userRepository;
    final ProfessionRepository professionRepository;

    public UserController(UserRepository userRepository, ProfessionRepository professionRepository) {
        this.userRepository = userRepository;
        this.professionRepository = professionRepository;
    }

    @GetMapping("/me")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<User> getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String mail = ((UserDetailsImpl) authentication.getPrincipal()).getEmail();
        final Optional<User> user = userRepository.findByEmail(mail);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody UpdateProfilePayload payload){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String mail = ((UserDetailsImpl) authentication.getPrincipal()).getEmail();

        final Optional<User> userOptional = userRepository.findByEmail(mail);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User not found!");
        }
        final User user = userOptional.get();

        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setAge(payload.getAge());

        if (payload.getProfessionId() != 0){
            final Optional<Profession> newProfession = professionRepository.findById(payload.getProfessionId());
            if (newProfession.isEmpty()){
                throw new NotFoundException("Profession not found!");
            }
            user.setProfession(newProfession.get());
        }

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found!");
        }
        final Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found!");
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
