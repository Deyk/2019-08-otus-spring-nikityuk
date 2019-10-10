package ru.otus.spring.homework0105.dao.impl;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.homework0105.dao", "ru.otus.spring.homework0105.settings"})
public class TestDaoContextConfig {
}
