package com.example.eshop.controller;

import com.example.eshop.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final AppConfig config;

    @Autowired
    public ProductController(AppConfig config) {
        this.config = config;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("App is running in %s environment".formatted(config.getEnvironment()));
    }

}
