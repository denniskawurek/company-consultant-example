package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;

/**
 * This class represents a company entity.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    private int companyId;

    private String companyName;

    private String address;

    /**
     * Constructor for an entity of a company without an id.
     * @param companyName The name of the company.
     * @param address The address of the company
     */
    public Company(String companyName, String address) {
        this.companyName = companyName;
        this.address = address;
    }

}