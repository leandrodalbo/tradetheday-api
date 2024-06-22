package com.tradetheday.repository;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
public class OpportunityRepositoryTest {

    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    @Autowired
    private OpportunityRepository repository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", OpportunityRepositoryTest::postgresUrl);
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);

    }

    private static String postgresUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                container.getContainerIpAddress(),
                container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                container.getDatabaseName());
    }

    @Test
    void willFindById() {

        StepVerifier.create(repository.findById(Opportunity.generateId("BTCUSDT", Timeframe.H1)))
                .expectNextMatches(opportunity -> opportunity.opportunityid().equals("BTCUSDT-H1"))
                .verifyComplete();
    }
}
