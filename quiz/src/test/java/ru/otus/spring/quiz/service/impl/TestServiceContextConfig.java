package ru.otus.spring.quiz.service.impl;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.quiz.service"})
public class TestServiceContextConfig {
}
