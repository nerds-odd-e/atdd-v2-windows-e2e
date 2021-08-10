package com.odde.atddv2;

import lombok.SneakyThrows;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

@Configuration
public class Factories {

    @Autowired
    StandaloneDevApi standaloneDevApi;

    @Bean
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>() {{
            add((request, body, execution) -> {
                request.getHeaders().add("token", Api.getToken());
                return execution.execute(request, body);
            });
        }});
        return restTemplate;
    }

    @SneakyThrows
    @Bean
    public MockServerClient createMockServerClient(@Value("${mock-server.endpoint}") String endpoint) {
        URL url = new URL(endpoint);
        return new MockServerClient(url.getHost(), url.getPort()) {
            @Override
            public void close() {
            }
        };
    }

    @Bean
    public Clock createMockServerClock() {
        return new Clock() {
            @Override
            public ZoneId getZone() {
                return null;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return null;
            }

            @Override
            public Instant instant() {
                try {
                    return standaloneDevApi.getClock();
                } catch (Throwable t) {
                    return Instant.now();
                }
            }
        };
    }
}
