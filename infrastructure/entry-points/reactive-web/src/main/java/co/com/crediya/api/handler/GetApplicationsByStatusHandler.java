package co.com.crediya.api.handler;

import co.com.crediya.usecase.getapplicationsbystatus.GetApplicationsByStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetApplicationsByStatusHandler {

    private static final String LOG_PREFIX = "[GetApplicationsByStatusHandler]";
    private final GetApplicationsByStatusUseCase getApplicationsByStatusUseCase;

    public Mono<ServerResponse> listenGetApplicationsByStatusHandler(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("10"));
        String statusName = serverRequest.queryParam("status").orElse("");

        return getApplicationsByStatusUseCase.getApplicationsByStatus(statusName, page, size)
                .flatMap(applications -> ServerResponse.ok().bodyValue(applications))
                .doOnSubscribe(subscription -> System.out.println(LOG_PREFIX + " - Start processing request"))
                .doOnSuccess(response -> System.out.println(LOG_PREFIX + " - Successfully processed request"))
                .doOnError(throwable -> System.err.println(LOG_PREFIX + " - Error processing request: " + throwable.getMessage())
        );
    }

}
