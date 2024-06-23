package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "guardian_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuardianType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    String type;

    @Column(nullable = false, unique = true)
    String name;

    @OneToMany(mappedBy = "guardianType", cascade = CascadeType.ALL)
    private Set<Guardian> guardians;
}
