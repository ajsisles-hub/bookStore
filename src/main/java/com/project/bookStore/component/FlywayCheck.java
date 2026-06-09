package com.project.bookStore.component;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayCheck implements CommandLineRunner {
    private final Flyway flyway;

    public FlywayCheck(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    public void run(String... args) {
        System.out.println("========== FLYWAY BEAN FOUND ==========");
        System.out.println(flyway.info().current());
    }
}

