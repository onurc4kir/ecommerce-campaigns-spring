package dev.onurcakir.ecommerce.payload.request;

import dev.onurcakir.ecommerce.model.Profession;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCampaignPayload {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private int advantageousAgeStart;
    @NotBlank
    private int advantageousAgeEnd;
    private Set<Profession> advantageousProfessions = new HashSet<>();
}
