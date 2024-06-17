package com.open.trade.repository;

import com.open.trade.model.TradeResult;
import com.open.trade.model.TradeStatus;
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
    private TradeRepository repository;

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
    public void willFindOpenTrades() {
        StepVerifier.create(repository.findByTradestatus(TradeStatus.OPEN))
                .expectNextMatches(it -> TradeStatus.OPEN.equals(it.tradestatus()))
                .verifyComplete();
    }

    @Test
    public void willFindSuccessfulTrades() {
        StepVerifier.create(repository.findByTraderesult(TradeResult.SUCCESS))
                .expectNextMatches(it -> TradeResult.SUCCESS.equals(it.traderesult()))
                .verifyComplete();
    }

}
