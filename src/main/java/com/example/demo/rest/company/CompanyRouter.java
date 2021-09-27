package com.example.demo.rest.company;

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
 * Configuration for company routes.
 */
@Configuration
public class CompanyRouter {
    @Autowired
    CompanyHandler companyHandler;

    @RouterOperations({
            @RouterOperation(
                    path = "/company/{companyId}",
                    method = RequestMethod.GET,
                    beanClass = CompanyHandler.class,
                    beanMethod = "getCompany",
                    operation = @Operation(
                            operationId = "getCompany", summary = "Find company by id.", tags = { "Company" },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "companyId", description = "Company name") }
                    )
            ),
            @RouterOperation(
                    path = "/company",
                    method = RequestMethod.POST,
                    beanClass = CompanyHandler.class,
                    beanMethod = "createCompany",
                    operation = @Operation(operationId = "createCompany", summary = "Create a new company.", tags = { "Company" })
            ),
            @RouterOperation(
                    path = "/company/{companyId}",
                    method = RequestMethod.PUT,
                    beanClass = CompanyHandler.class,
                    beanMethod = "updateCompany",
                    operation = @Operation(
                            operationId = "updateCompany", summary = "Update an existing company.", tags = { "Company" },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "companyId", description = "Company name") })
            ),
            @RouterOperation(
                    path = "/company/{companyName}",
                    method = RequestMethod.DELETE,
                    beanClass = CompanyHandler.class,
                    beanMethod = "deleteCompany",
                    operation = @Operation(
                            operationId = "deleteCompany", summary = "Delete company by name.", tags = { "Company" },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "companyName", description = "Company name") }
                    )
            )
    })

    @Bean
    RouterFunction<ServerResponse> companyRouterFunctions() {
        return RouterFunctions.
                route()
                .GET("/company/{companyId}", request -> companyHandler.getCompany(Integer.parseInt(request.pathVariable("companyId"))))
                .POST("/company", request -> companyHandler.createCompany(request.bodyToMono(CompanyHandler.CompanyDTO.class)))
                .PUT("/company/{companyId}", request -> companyHandler.updateCompany(Integer.parseInt(request.pathVariable("companyId")), request.bodyToMono(CompanyHandler.CompanyDTO.class)))
                .DELETE("/company/{companyId}", request -> companyHandler.deleteCompany(Integer.parseInt(request.pathVariable("companyId"))))
                .build();
    }
}
