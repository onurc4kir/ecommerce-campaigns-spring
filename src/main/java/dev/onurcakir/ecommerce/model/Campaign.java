package dev.onurcakir.ecommerce.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String name;
    @Nullable
    private String description;

    private int advantageousAgeStart = 0;
    private int advantageousAgeEnd = 120;
    @ManyToMany
    @JoinTable(
            name = "campaign_profession",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "profession_id"))
    private Set<Profession> advantageousProfessions;


}
