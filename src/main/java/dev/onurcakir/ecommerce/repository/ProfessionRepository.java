package dev.onurcakir.ecommerce.repository;

import dev.onurcakir.ecommerce.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
        Optional<Profession> findProfessionByName(String name);
        Boolean existsByName(String name);
        }