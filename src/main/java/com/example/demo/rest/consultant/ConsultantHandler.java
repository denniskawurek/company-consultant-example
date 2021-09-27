package com.example.demo.rest.consultant;

import com.example.demo.model.Company;
import com.example.demo.model.Consultant;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ConsultantRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * REST controller to fetch, create and update {@link Consultant}s.
 */
@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsultantHandler {

    @Autowired
    ConsultantRepository consultantRepository;

    @Autowired
    CompanyRepository companyRepository;

    /**
     * Get a consultant by id.
     *
     * @param consultantId Id of the consultant.
     * @return Mono with a response.
     */
    public Mono<ServerResponse> getConsultant(Integer consultantId) {
        Optional<Consultant> optionalConsultant = consultantRepository.findById(consultantId);

        if (optionalConsultant.isEmpty()) {
            log.error("Tried to fetch consultant with id {} which doesn't exist", consultantId);
            return ServerResponse.notFound().build();
        }

        return ServerResponse.ok().bodyValue(optionalConsultant.get());
    }

    /**
     * Create a new consultant.
     *
     * @param consultantDTOMono The DTO which contains all consultant information.
     * @return Mono with a ServerResponse.
     */
    public Mono<ServerResponse> createConsultant(Mono<ConsultantDTO> consultantDTOMono) {
        return consultantDTOMono
                .flatMap(consultantDTO -> {
                    Company company = null;
                    if (consultantDTO.getCompanyId() != null) {
                        Optional<Company> optionalCompany = companyRepository.findById(consultantDTO.getCompanyId());
                        if (optionalCompany.isEmpty()) {
                            log.error("Failed to create a consultant. Company with the id {} doesn't exist.", consultantDTO.getCompanyId());
                            return ServerResponse.badRequest().build();
                        }
                        company = optionalCompany.get();
                    }

                    Consultant consultant = new Consultant(consultantDTO.getFirstName(), consultantDTO.getLastName(), consultantDTO.getCompanyId());

                    consultantRepository.save(consultant);

                    return ServerResponse.ok().build();
                });
    }

    /**
     * Updates a consultant.
     *
     * @param consultantId Id of the consultant.
     * @param consultantDTOMono The updated consultant object.
     * @return A ServerResponse with the according request information.
     */
    public Mono<ServerResponse> updateConsultant(int consultantId, Mono<ConsultantDTO> consultantDTOMono) {
        return consultantDTOMono.flatMap(consultantDTO -> {
            Optional<Consultant> optionalConsultant = consultantRepository.findById(consultantId);
            if (optionalConsultant.isEmpty()) {
                log.error("Tried to update a consultant with id {} which not exists.", consultantId);
                return ServerResponse.notFound().build();
            }

            if (consultantDTO.getCompanyId() != null) {
                if (companyRepository.findById(consultantDTO.getCompanyId()).isEmpty()) {
                    log.error("Failed to create a consultant. Company with the id {} doesn't exist.", consultantDTO.getCompanyId());
                    return ServerResponse.badRequest().build();
                }
            }

            Consultant consultant = optionalConsultant.get();
            consultant.setFirstName(consultantDTO.getFirstName());
            consultant.setLastName(consultantDTO.getLastName());
            consultant.setCompanyId(consultantDTO.getCompanyId());

            consultantRepository.save(consultant);

            return ServerResponse.ok().bodyValue(consultantDTO);
        });
    }

    /**
     * Deletes a consultant by id.
     * @param consultantId The id of the consultant to delete.
     * @return A mono with the according ServerResponse.
     */
    public Mono<ServerResponse> deleteConsultant(Integer consultantId) {
        Optional<Consultant> optionalConsultant = consultantRepository.findById(consultantId);

        if (optionalConsultant.isEmpty()) {
            log.error("Tried to delete consultant with id {} which doesn't exist", consultantId);
            return ServerResponse.notFound().build();
        }

        consultantRepository.deleteById(consultantId);

        return ServerResponse.ok().bodyValue(optionalConsultant.get());
    }

    /**
     * DTO object for transferring consultant information.
     */
    @Data
    public static class ConsultantDTO {
        private String firstName;
        private String lastName;
        private Integer companyId;
    }
}
