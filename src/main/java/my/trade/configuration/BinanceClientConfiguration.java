package my.trade.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BinanceClientConfiguration {

    @Bean
    WebClient webClient(WebClient.Builder builder, ProjectProps projectProps) {
        return builder.baseUrl(projectProps.binanceUri()).build();
    }
}