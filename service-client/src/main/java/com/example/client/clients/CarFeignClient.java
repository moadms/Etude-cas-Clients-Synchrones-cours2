package com.example.client.clients;

import com.example.client.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SERVICE-VOITURE")
public interface CarFeignClient {

    @GetMapping("/api/cars/byClient/{clientId}")
    Car getCarByClient(@PathVariable("clientId") Long clientId);
}
