package ru.otus.spring.homework0105.service.impl;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.homework0105.service.MessageService;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.homework0105.service"})
public class TestServiceContextConfig {
}
