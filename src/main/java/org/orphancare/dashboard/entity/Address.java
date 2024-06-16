package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Size(max = 255)
    private String street;

    @Size(max = 255)
    private String urbanVillage;

    @Size(max = 255)
    private String subdistrict;

    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String province;

    @Size(max = 5)
    private String postalCode;
}
