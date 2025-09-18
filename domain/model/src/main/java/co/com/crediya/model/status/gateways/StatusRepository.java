package co.com.crediya.model.status.gateways;

import co.com.crediya.model.status.Status;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StatusRepository {
    Mono<Status> findByName(String name);
    Flux<Status> findAllById(List<Integer> ids);
}
