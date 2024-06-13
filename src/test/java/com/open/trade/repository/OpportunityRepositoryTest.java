package com.open.trade.repository;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@DataR2dbcTest
@Testcontainers
public class OpportunityRepositoryTest {

    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));


    @Autowired
    private OpportunityRepository repository;

    private static String postgresUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                container.getContainerIpAddress(),
                container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                container.getDatabaseName());
    }


    @DynamicPropertySource
    static void setlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", OpportunityRepositoryTest::postgresUrl);
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
        registry.add("spring.datasource.url", OpportunityRepositoryTest::postgresUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.liquibase.enabled", () -> true);
    }

    @Test
    public void willFindOpportunitiesBySymbolsAndSpeed() {
    }
}
