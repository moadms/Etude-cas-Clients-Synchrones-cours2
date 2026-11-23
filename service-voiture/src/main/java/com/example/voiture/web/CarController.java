package com.example.voiture.web;

import com.example.voiture.model.Car;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @GetMapping("/byClient/{clientId}")
    public Car getCarByClient(
            @PathVariable Long clientId,
            @RequestParam(defaultValue = "20") long delayMs) throws InterruptedException {
        if (delayMs > 0) {
            Thread.sleep(delayMs);
        }
        return new Car(10L, "Toyota", "Yaris", clientId);
    }
}
