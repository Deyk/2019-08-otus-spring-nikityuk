package ru.otus.spring.library.config;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoBeeConfig {
    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee(uri);
        runner.setDbName(database);         // host must be set if not set in URI
        runner.setChangeLogsScanPackage("ru.otus.spring.library.config.changelogs"); // the package to be scanned for changesets
        runner.setMongoTemplate(mongoTemplate);
        return runner;
    }
}
