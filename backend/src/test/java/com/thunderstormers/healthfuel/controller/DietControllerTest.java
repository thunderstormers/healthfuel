package com.thunderstormers.healthfuel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thunderstormers.healthfuel.models.Gender;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;

import jakarta.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DietController.class) // Load only DietController
class DietControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper for JSON serialization
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void printBeans() {
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }
    @Test
    void testPersonalizedDietEndpoint() throws Exception {
        // Create request payload
        // UserDietDetailsRequest request = new UserDietDetailsRequest(
        //         "john_doe",
        //         30,
        //         75,
        //         180,
        //         Gender.Male,
        //         List.of("Peanuts", "Shellfish"),
        //         List.of("Hypertension"),
        //         "Moderate",
        //         "",
        //         "Weight Loss"
        // );

        // // Perform the POST request
        // mockMvc.perform(post("/api/personalized-diet")
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(objectMapper.writeValueAsString(request)) // Convert Java object to JSON
        //                 .accept(MediaType.APPLICATION_JSON))
        //         .andExpect(status().isOk()) // Verify status is 200 OK
        //         .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Ensure response is JSON
    }
}
