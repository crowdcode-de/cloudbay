package io.crowdcode.cloudbay;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@SpringBootApplication
@RequiredArgsConstructor
public class SecureGatewayApplication {

    private final TokenRelayGatewayFilterFactory filterFactory;

//    @Bean
//    public RouteLocator customRoutLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route( p -> p
//                        .path("/userinfo/**","/userinfo")
//                        .filters( f -> f
//                                .filters(filterFactory.apply())
//                                .removeRequestHeader("Cookie") // do not send cookies down streamed
//                                .addRequestHeader("SECURE-GATEWAY","CHECKED"))
//                        .uri("http://localhost:8100")
//                ).build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(SecureGatewayApplication.class, args);
    }

}
