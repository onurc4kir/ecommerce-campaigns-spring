package dev.onurcakir.ecommerce.repository;

import dev.onurcakir.ecommerce.model.Campaign;
import dev.onurcakir.ecommerce.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findCampaignByNameContains(String name);
    @Query("SELECT c FROM Campaign c " +
            "LEFT JOIN c.advantageousProfessions p " +
            "ORDER BY (CASE WHEN ((c.advantageousAgeStart <= :age AND c.advantageousAgeEnd >= :age) OR p.id = :professionId) THEN 0 ELSE 1 END)")
    Page<Campaign> findAll(@Param("age") int age, @Param("professionId") long professionId, Pageable pageable);
}
