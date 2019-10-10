package ru.otus.spring.quiz.dao.impl;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.quiz.dao", "ru.otus.spring.quiz.settings"})
public class TestDaoContextConfig {
}
