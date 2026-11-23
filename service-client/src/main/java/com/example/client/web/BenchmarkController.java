package com.example.client.web;

import com.example.client.clients.CarFeignClient;
import com.example.client.model.Car;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/clients")
public class BenchmarkController {

    private final RestTemplate restTemplate;
    private final CarFeignClient carFeignClient;
    private final WebClient.Builder webClientBuilder;

    public BenchmarkController(RestTemplate restTemplate,
                               CarFeignClient carFeignClient,
                               WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.carFeignClient = carFeignClient;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/{id}/car/rest")
    public Car getCarViaRestTemplate(@PathVariable Long id) {
        return restTemplate.getForObject("http://SERVICE-VOITURE/api/cars/byClient/" + id, Car.class);
    }

    @GetMapping("/{id}/car/feign")
    public Car getCarViaFeign(@PathVariable Long id) {
        return carFeignClient.getCarByClient(id);
    }

    @GetMapping("/{id}/car/webclient")
    public Car getCarViaWebClient(@PathVariable Long id) {
        return webClientBuilder.build()
                .get()
                .uri("http://SERVICE-VOITURE/api/cars/byClient/" + id)
                .retrieve()
                .bodyToMono(Car.class)
                .block();
    }
}
