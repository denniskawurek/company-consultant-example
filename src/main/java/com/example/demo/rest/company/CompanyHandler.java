package com.example.demo.rest.company;

import com.example.demo.model.Company;
import com.example.demo.repository.CompanyRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

/**
 * REST controller to fetch, create and update {@link Company}s.
 */
@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyHandler {
    @Autowired
    CompanyRepository companyRepository;

    /**
     * Fetches the company with the passed name.
     *
     * @param companyId The id of the company to fetch.
     * @return A ServerResponse with the company name.
     */
    public Mono<ServerResponse> getCompany(Integer companyId) {
        if (Objects.equals(companyId, null)) {
            log.error("Passed param for company is null.");
            return ServerResponse.badRequest().build();
        }

        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isEmpty()) {
            log.error("No company with id {} found.", companyId);
            return ServerResponse.notFound().build();
        }
        return ServerResponse.ok().bodyValue(optionalCompany.get());
    }

    /**
     * Creates a new company.
     *
     * @param companyDTOMono A mono which contains the optionalCompany.
     * @return A ServerResponse success status.
     */
    public Mono<ServerResponse> createCompany(Mono<CompanyDTO> companyDTOMono) {
        return companyDTOMono
                .flatMap(companyDTO -> {
                    Company company = new Company(companyDTO.getCompanyName(), companyDTO.getAddress());

                    companyRepository.save(company);

                    return ServerResponse.ok().build();
                });
    }

    /**
     * Updates a company.
     *
     * @param companyId The id of the company to update.
     * @param companyDTOMono The company to update.
     * @return A mono with the according response.
     */
    public Mono<ServerResponse> updateCompany(int companyId, Mono<CompanyDTO> companyDTOMono) {
        return companyDTOMono.flatMap(companyDTO -> {
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isEmpty()) {
                log.error("Tried to update a company with id {} which not exists.", companyId);
                return ServerResponse.notFound().build();
            }

            Company company = optionalCompany.get();
            company.setCompanyName(companyDTO.getCompanyName());
            company.setAddress(companyDTO.getAddress());

            companyRepository.save(company);

            return ServerResponse.ok().bodyValue(companyDTO);
        });
    }

    /**
     * Deletes a company by name.
     *
     * @param companyId The id of the company to delete.
     * @return A Mono with a response stauts.
     */
    public Mono<ServerResponse> deleteCompany(Integer companyId) {
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isEmpty()) {
                log.error("Tried to delete a company with id {} which not exists.", companyId);
                return ServerResponse.notFound().build();
            }
            companyRepository.deleteById(companyId);

            return ServerResponse.ok().build();
    }

    /**
     * DTO for requests/responses
     */
    @Data
    public static class CompanyDTO {
        private String companyName;
        private String address;
    }
}
