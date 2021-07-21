package org.serverwizard.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public CustomAuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Request Header 에 token 이 존재하지 않을 때
//            if(!request.getHeaders().containsKey("token")){
//                return handleUnAuthorized(exchange); // 401 Error
//            }

            // Request Header 에서 token 문자열 받아오기
            List<String> token = request.getHeaders().get("token");
//            String tokenString = Objects.requireNonNull(token).get(0);

            // 토큰 검증
//            if(tokenString.equals("A.B.C")) {
//                return handleUnAuthorized(exchange); // 토큰이 일치하지 않을 때
//            }

            String login = "login";
            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8181/login")
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(string -> {
                        log.info("response: {}", string);
                        if (string.equals(login)) {
                            log.info("login ok...");
                        } else {
                            throw new IllegalArgumentException("잘못 id/pw 입력");
                        }
                        return exchange;
                    })
                    .log()
                    .flatMap(chain::filter);
        });
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public static class Config {
    }
}
