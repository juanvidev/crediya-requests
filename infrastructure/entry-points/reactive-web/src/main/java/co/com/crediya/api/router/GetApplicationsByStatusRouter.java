package co.com.crediya.api.router;

import co.com.crediya.api.docs.LoanApplicationDocs;
import co.com.crediya.api.handler.GetApplicationsByStatusHandler;
import co.com.crediya.api.handler.LoanApplicationHandler;
import co.com.crediya.api.util.Routes;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GetApplicationsByStatusRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/apply",
                    method = RequestMethod.POST,
                    beanClass = LoanApplicationDocs.class,
                    beanMethod = "createLoanApplication"
            )
    })
    public RouterFunction<ServerResponse> routerGetApplicationsByStatusFunction(GetApplicationsByStatusHandler getApplicationsByStatusHandler) {
        return RouterFunctions
                .route()
                .path("/api/v1/applications",builder -> builder
                    .GET("", getApplicationsByStatusHandler::listenGetApplicationsByStatusHandler))
                .build();
    }}
