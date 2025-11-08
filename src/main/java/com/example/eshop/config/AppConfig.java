package com.example.eshop.config;

import com.example.eshop.enums.Environment;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    /** The Application environment. */
    private Environment environment;

    /** The Application description. */
    private String description;

}
