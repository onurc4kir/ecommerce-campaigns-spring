package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.exception.model.NotFoundException;
import dev.onurcakir.ecommerce.model.Campaign;
import dev.onurcakir.ecommerce.payload.request.CreateCampaignPayload;
import dev.onurcakir.ecommerce.repository.CampaignRepository;
import dev.onurcakir.ecommerce.repository.UserRepository;
import dev.onurcakir.ecommerce.security.service.UserDetailsImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import dev.onurcakir.ecommerce.model.User;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/campaign")
public class CampaignController {

    CampaignRepository campaignRepository;
    UserRepository userRepository;
    public CampaignController(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    @Cacheable(value = "getCampaignsCache",keyGenerator = "campaignGetKeyGenerator")
    public ResponseEntity<?> getCampaigns(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Check if user is authenticated


        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){

            String mail = ((UserDetailsImpl)authentication.getPrincipal()).getEmail();
            Optional<User> userOptional = userRepository.findByEmail(mail);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                long userProfessionId = user.getProfession() != null ? user.getProfession().getId() : 0;
                int userAge = user.getAge();
                Page<Campaign> campaigns = campaignRepository.findAll(userAge,userProfessionId,pageRequest);
                return ResponseEntity.ok(campaigns);
            }

        }
        Page<Campaign> campaigns = campaignRepository.findAll(pageRequest);
        return ResponseEntity.ok(campaigns);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable long id) {
        final Campaign campaign = campaignRepository.findById(id).get();
        if (campaign == null) {
            throw new NotFoundException("Campaign not found!");
        }
        return ResponseEntity.ok(campaign);
    }
    @DeleteMapping("/{id}")
    @CacheEvict(value = "getCampaignsCache", allEntries = true)
    public ResponseEntity<?> deleteCampaign(@PathVariable long id) {
        final Campaign campaign = campaignRepository.findById(id).get();
        if (campaign == null) {
            throw new NotFoundException("Campaign not found!");
        }
        campaignRepository.delete(campaign);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    @CacheEvict(value = "getCampaignsCache", allEntries = true)
    public ResponseEntity<Campaign> createCampaign(@RequestBody CreateCampaignPayload campaign) {

        Campaign newCampaign = new Campaign();

        newCampaign.setName(campaign.getName());
        newCampaign.setDescription(campaign.getDescription());
        newCampaign.setAdvantageousAgeStart(campaign.getAdvantageousAgeStart());
        newCampaign.setAdvantageousAgeEnd(campaign.getAdvantageousAgeEnd());
        newCampaign.setAdvantageousProfessions(campaign.getAdvantageousProfessions());

        campaignRepository.save(newCampaign);

        return ResponseEntity.status(201).body(newCampaign);
    }
    @PatchMapping("/{id}")
    @CacheEvict(value = "getCampaignsCache", allEntries = true)
    public ResponseEntity<Campaign> updateCampaign(@PathVariable long id, @RequestBody Campaign payload) {
        final Campaign campaign = campaignRepository.findById(id).get();

        if (campaign == null) {
            throw new NotFoundException("Campaign not found!");
        }

        final Campaign updatedCampaign = campaignRepository.save(payload);
        return ResponseEntity.ok(updatedCampaign);
    }



}
