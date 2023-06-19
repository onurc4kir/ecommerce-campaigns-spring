package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.exception.model.BadRequestException;
import dev.onurcakir.ecommerce.exception.model.NotFoundException;
import dev.onurcakir.ecommerce.model.Role;
import dev.onurcakir.ecommerce.model.User;
import dev.onurcakir.ecommerce.payload.request.LoginPayload;
import dev.onurcakir.ecommerce.payload.request.SignupPayload;
import dev.onurcakir.ecommerce.payload.response.AccessTokenResponse;
import dev.onurcakir.ecommerce.payload.response.MessageResponse;
import dev.onurcakir.ecommerce.repository.RoleRepository;
import dev.onurcakir.ecommerce.repository.UserRepository;
import dev.onurcakir.ecommerce.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    final AuthenticationManager authenticationManager;

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder encoder;

    final JwtUtils jwtUtils;

    public AuthController(PasswordEncoder encoder, JwtUtils jwtUtils, RoleRepository roleRepository, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginPayload loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new AccessTokenResponse(
                jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupPayload signupPayload) {
        if (userRepository.existsByEmail(signupPayload.getEmail())) {
            throw new BadRequestException("Email is already in use!");
        }

        User user = new User(
                signupPayload.getFirstName(),
                signupPayload.getLastName(),
                signupPayload.getEmail(),
                encoder.encode(signupPayload.getPassword()),
                signupPayload.getAge(),
                signupPayload.getProfession()
        );

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository
                .findRoleByName("user")
                .orElseThrow(() -> new NotFoundException("Role is not found.")));

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
}
