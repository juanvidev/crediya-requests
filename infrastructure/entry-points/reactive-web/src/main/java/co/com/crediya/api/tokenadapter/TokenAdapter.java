package co.com.crediya.api.tokenadapter;

import co.com.crediya.model.clientrest.gateways.TokenGateway;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TokenAdapter implements TokenGateway {
    @Override
    public Mono<String> getToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getCredentials().toString());
    }

    @Override
    public Mono<String> getClaim(String claimName) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getCredentials())
                .flatMap(credentials -> {
                    if(credentials instanceof Jwt jwt){
                        return Mono.justOrEmpty(jwt.getClaimAsString(claimName));
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
