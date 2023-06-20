package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.exception.model.NotFoundException;
import dev.onurcakir.ecommerce.model.Profession;
import dev.onurcakir.ecommerce.model.User;
import dev.onurcakir.ecommerce.payload.request.UpdateProfilePayload;
import dev.onurcakir.ecommerce.payload.response.MessageResponse;
import dev.onurcakir.ecommerce.repository.ProfessionRepository;
import dev.onurcakir.ecommerce.repository.UserRepository;
import dev.onurcakir.ecommerce.security.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String mail = ((UserDetailsImpl) authentication.getPrincipal()).getEmail();
        final User user = userRepository.findByEmail(mail).get();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody UpdateProfilePayload payload){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String mail = ((UserDetailsImpl) authentication.getPrincipal()).getEmail();

        final User user = userRepository.findByEmail(mail).get();

        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setAge(payload.getAge());

        if (payload.getProfessionId() != 0 && payload.getProfessionId() != user.getProfession().getId()){
            final Profession newProfession = professionRepository.findById(payload.getProfessionId()).get();
            if (newProfession == null){
                throw new NotFoundException("Profession not found!");
            }
            user.setProfession(newProfession);
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
        final User user = userRepository.findById(id).get();
        return ResponseEntity.ok(user);
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
