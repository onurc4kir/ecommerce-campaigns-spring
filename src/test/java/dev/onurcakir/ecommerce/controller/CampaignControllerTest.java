package dev.onurcakir.ecommerce.controller;

import dev.onurcakir.ecommerce.model.Campaign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CampaignControllerTest {
    @Autowired
    CampaignController campaignController;

    @Test
    void getCampaignById() {
        long campaignId = 1;

        // Fetch campaign by ID
        ResponseEntity<Campaign> response = campaignController.getCampaignById(campaignId);

        // Check if response is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check if response body is not null
        assertNotNull(response.getBody());

        // Check if campaign ID is equal to 1
        assertEquals(campaignId, response.getBody().getId());
    }

    @Test
    void getCampaigns() {
        int page = 0;
        int size = 10;

        // Fetch campaigns
        ResponseEntity<?> response = campaignController.getCampaigns(page, size);

        // Check if response is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check if response body is not null
        assertNotNull(response.getBody());
    }
}