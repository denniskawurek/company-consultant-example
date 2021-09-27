package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class represents a consultant entity.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Consultant {
    @Id
    @GeneratedValue
    private Integer consultantId;

    private String firstName;
    private String lastName;

    private Integer companyId;

    @ManyToOne
    @JoinColumn(name="companyId", referencedColumnName="companyId", insertable = false, updatable = false)
    private Company company;

    /**
     * Constructor for a new, not stored, Consultant instance.
     * @param firstName Firstname of the consultant.
     * @param lastName Lastname of the consultant.
     * @param companyId Id of the company to which the consultant belongs.
     */
    public Consultant(String firstName, String lastName, Integer companyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyId = companyId;
    }
}
