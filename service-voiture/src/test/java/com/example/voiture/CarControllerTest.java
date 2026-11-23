package com.example.voiture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "eureka.client.enabled=false")
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCarForClient() throws Exception {
        mockMvc.perform(get("/api/cars/byClient/1?delayMs=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marque").value("Toyota"))
                .andExpect(jsonPath("$.modele").value("Yaris"))
                .andExpect(jsonPath("$.clientId").value(1));
    }
}
