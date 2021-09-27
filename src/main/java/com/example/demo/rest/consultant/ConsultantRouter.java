package com.example.demo.rest.consultant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Configuration for consultant routes.
 */
@Configuration
public class ConsultantRouter {
    @Autowired
    ConsultantHandler consultantHandler;

    @RouterOperations({
            @RouterOperation(
                    path = "/consultant/{consultantId}",
                    method = RequestMethod.GET,
                    beanClass = ConsultantHandler.class,
                    beanMethod = "getConsultant",
                    operation = @Operation(
                            operationId = "getConsultant", summary = "Find consultant by name.", tags = { "Consultant" },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "consultantId", description = "Consultant name") }
                    )
            ),
            @RouterOperation(
                    path = "/consultant",
                    method = RequestMethod.POST,
                    beanClass = ConsultantHandler.class,
                    beanMethod = "createConsultant",
                    operation = @Operation(operationId = "createConsultant", summary = "Create a new consultant.", tags = { "Consultant" })
            ),
            @RouterOperation(
                    path = "/consultant/{consultantId}",
                    method = RequestMethod.PUT,
                    beanClass = ConsultantHandler.class,
                    beanMethod = "updateConsultant",
                    operation = @Operation(operationId = "updateConsultant", summary = "Update an existing consultant.", tags = { "Consultant" },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "consultantId", description = "Consultant id") })
            ),
            @RouterOperation(
                    path = "/consultant/{consultantId}",
                    method = RequestMethod.DELETE,
                    beanClass = ConsultantHandler.class,
                    beanMethod = "deleteConsultant",
                    operation = @Operation(
                            operationId = "deleteConsultant", summary = "Delete consultant by name.", tags = { "Consultant" },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "consultantId", description = "Consultant id") }
                    )
            )
    })

    @Bean
    RouterFunction<ServerResponse> consultantRouterFunctions() {
        return RouterFunctions.
                route()
                .GET("/consultant/{consultantId}", request -> consultantHandler.getConsultant(Integer.parseInt(request.pathVariable("consultantId"))))
                .POST("/consultant", request -> consultantHandler.createConsultant(request.bodyToMono(ConsultantHandler.ConsultantDTO.class)))
                .PUT("/consultant/{consultantId}", request -> consultantHandler.updateConsultant(Integer.parseInt(request.pathVariable("consultantId")), request.bodyToMono(ConsultantHandler.ConsultantDTO.class)))
                .DELETE("/consultant/{consultantId}", request -> consultantHandler.deleteConsultant(Integer.parseInt(request.pathVariable("consultantId"))))
                .build();
    }
}
