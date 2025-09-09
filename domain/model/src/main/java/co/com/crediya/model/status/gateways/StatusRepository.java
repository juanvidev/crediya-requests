package co.com.crediya.model.status.gateways;

import co.com.crediya.model.status.Status;
import reactor.core.publisher.Mono;

public interface StatusRepository {
    Mono<Status> findByName(String name);
    Mono<Status> findById(Integer id);
}
