package co.com.crediya.api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


@Log4j2
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class AuthorizationJwt implements WebFluxConfigurer {

    private final String jsonExpRoles;
    private final String url;

    private final int timeout;
    private final ObjectMapper mapper;
    private static final String ROLE = "ROLE_";
    private static final String SUB = "sub";
    private final SecretKey secretKey;

    public AuthorizationJwt(@Value("${jwt.json-exp-roles}") String jsonExpRoles, ObjectMapper mapper, SecretKey secretKey, @Value("${adapter.restconsumer.url}") String url, @Value("${adapter.restconsumer.timeout}") int timeout) {

        this.jsonExpRoles = jsonExpRoles;
        this.mapper = mapper;
        this.secretKey = secretKey;
        this.url = url;
        this.timeout = timeout;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/apply").hasRole("CLIENT")
                        .pathMatchers("/api/v1/applications").hasRole("ADVISOR")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtSpec -> jwtSpec
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                        )
                );

        return http.build();
    }

    public ReactiveJwtDecoder jwtDecoder() {
        var tokenValidator = new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefault(),
                new JwtClaimValidator<String>(SUB, claim -> claim != null && !claim.isEmpty())
        );
        var jwtDecoder = NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .build();

        jwtDecoder.setJwtValidator(tokenValidator);
        return jwtDecoder;
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        var jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt ->
                getRoles(jwt.getClaims(), jsonExpRoles)
                        .stream()
                        .map(ROLE::concat)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        return new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
    }

    private List<String> getRoles(Map<String, Object> claims, String jsonExpClaim) {
        List<String> roles = List.of();
        try {
            var json = mapper.writeValueAsString(claims);
            var chunk = mapper.readTree(json).at(jsonExpClaim);
            return mapper.readerFor(new TypeReference<List<String>>() {
                    })
                    .readValue(chunk);
        } catch (IOException e) {
            log.error(e.getMessage());
            return roles;
        }
    }

    private ClientHttpConnector getClientHttpConnector() {
        return new ReactorClientHttpConnector(HttpClient.create()
                .compress(true)
                .keepAlive(true)
                .option(CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, MILLISECONDS));
                }));
    }

    @Bean
    public WebClient getWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .clientConnector(getClientHttpConnector())
                .filter((request, next) ->
                        ReactiveSecurityContextHolder.getContext()
                                .<String>handle((ctx, sink) -> {
                                    Object credentials = ctx.getAuthentication().getCredentials();
                                    if (credentials instanceof Jwt jwt) {
                                        sink.next(jwt.getTokenValue());
                                        return;
                                    }
                                    sink.error(new IllegalStateException("No valid JWT found in SecurityContext"));
                                })
                                .defaultIfEmpty("")
                                .flatMap(token -> {
                                    var requestWithAuth = ClientRequest.from(request)
                                            .headers(headers -> {
                                                if (!token.isBlank()) {
                                                    headers.setBearerAuth(token);
                                                }
                                            })
                                            .build();
                                    return next.exchange(requestWithAuth);
                                })
                )
                .build();
    }
}