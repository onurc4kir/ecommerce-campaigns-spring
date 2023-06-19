package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.exception.model.BadRequestException;
import dev.onurcakir.ecommerce.exception.model.NotFoundException;
import dev.onurcakir.ecommerce.model.Profession;
import dev.onurcakir.ecommerce.payload.request.ProfessionCreatePayload;
import dev.onurcakir.ecommerce.repository.ProfessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profession")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProfessionController {

    final ProfessionRepository professionRepository;

    public ProfessionController(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }


    @GetMapping("")
    public ResponseEntity<Page<Profession>> getAllProfessionsPaginated(@RequestParam int page, @RequestParam int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Profession> roles = professionRepository.findAll(pageable);
        return ResponseEntity.ok(roles);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Profession> getProfessionById(@PathVariable Long id) {
        if (!professionRepository.existsById(id)) {
            throw new NotFoundException("Profession not found!");
        }
        final Profession role = professionRepository.findById(id).get();
        return ResponseEntity.ok(role);
    }

    @PostMapping("")
    public ResponseEntity<?> createProfession(@RequestBody ProfessionCreatePayload payload) {
        if (professionRepository.existsByName(payload.getName())) {
            throw new BadRequestException("Profession is already exists!");
        }
        final Profession savedProfession = professionRepository.save(new Profession(payload.getName()));
        return ResponseEntity.ok(savedProfession);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProfession(@PathVariable Long id, @RequestBody Profession profession) {
        if (!professionRepository.existsById(id)) {
            throw new NotFoundException("Profession not found!");
        }
        final Profession savedProfession = professionRepository.save(profession);
        return ResponseEntity.ok(savedProfession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfession(@PathVariable Long id) {
        if (!professionRepository.existsById(id)) {
            throw new NotFoundException("Profession not found!");
        }
        professionRepository.deleteById(id);
        return ResponseEntity.ok("Profession deleted successfully!");
    }
}
