package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column()
    private String profilePicture;

    @NotNull
    @Column()
    private LocalDate birthday;

    @NotNull
    @Column()
    private LocalDate joinDate;

    @NotNull
    @Column()
    private LocalDate leaveDate;

    @NotNull
    @Column()
    private String bio;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
