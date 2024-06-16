package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    @Size(max = 255)
    private String urbanVillage;

    @NotBlank
    @Size(max = 255)
    private String subdistrict;

    @NotBlank
    @Size(max = 255)
    private String city;

    @NotBlank
    @Size(max = 255)
    private String province;

    @NotBlank
    @Size(max = 5)
    private String postalCode;
}
