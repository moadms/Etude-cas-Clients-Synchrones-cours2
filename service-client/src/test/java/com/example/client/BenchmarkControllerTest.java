package com.example.client;

import com.example.client.clients.CarFeignClient;
import com.example.client.model.Car;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "eureka.client.enabled=false")
@AutoConfigureMockMvc
class BenchmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CarFeignClient carFeignClient;

    @Test
    void shouldFetchCarViaRestTemplateAndFeign() throws Exception {
        Car mockCar = new Car(10L, "Toyota", "Yaris", 1L);
        Mockito.when(restTemplate.getForObject("http://SERVICE-VOITURE/api/cars/byClient/1", Car.class))
                .thenReturn(mockCar);
        Mockito.when(carFeignClient.getCarByClient(1L))
                .thenReturn(mockCar);

        mockMvc.perform(get("/api/clients/1/car/rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marque").value("Toyota"));

        mockMvc.perform(get("/api/clients/1/car/feign"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modele").value("Yaris"));
    }
}
