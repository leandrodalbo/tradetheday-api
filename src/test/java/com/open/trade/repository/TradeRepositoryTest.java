package com.open.trade.repository;


import com.open.trade.model.Speed;
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
public class TradeRepositoryTest {

    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));


    @Autowired
    private OpportunityRepository repository;

    private static String r2dbcUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                container.getContainerIpAddress(),
                container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                container.getDatabaseName());
    }

    @DynamicPropertySource
    static void setlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", TradeRepositoryTest::r2dbcUrl);
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
    }

    @Test
    public void willFindOpportunityBySymbol() {

        StepVerifier.create(repository.findBySymbol("BTCUSDT"))
                .expectNextMatches(opportunity -> opportunity.symbol().equals("BTCUSDT"))
                .verifyComplete();
    }

    @Test
    public void willNotFindOpportunityBySymbol() {
        StepVerifier.create(repository.findBySymbol("XSDSDT"))
                .expectError();
    }

    @Test
    public void willFindHighSpeedEngulfingEntries() {
        StepVerifier.create(repository.findEngulfingBySpeed(Speed.HIGH))
                .thenConsumeWhile(it -> it.krakenspeed().equals(Speed.HIGH) || it.binancespeed().equals(Speed.HIGH))
                .verifyComplete();
    }
}
