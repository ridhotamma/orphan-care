package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.validation.NoWhiteSpace;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String fullName;

    @Column()
    private String profilePicture;

    @Column()
    private LocalDate birthday;

    @Column()
    private LocalDate joinDate;

    @Column()
    private LocalDate leaveDate;

    @Column()
    private String bio;

    @Column()
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "bedroom_id")
    private BedRoom bedRoom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isCareTaker = false;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAlumni = false;

    @Size(min = 16, max = 16)
    @Column()
    @NoWhiteSpace
    private String nikNumber;

    @Size(min = 16, max = 16)
    @Column()
    @NoWhiteSpace
    private String kkNumber;

    @Enumerated(EnumType.STRING)
    @Column()
    private OrphanType orphanType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Getter
    public enum OrphanType {
        FATHERLESS,
        MOTHERLESS,
        BOTH_DECEASED,
        POOR;

        public String getDisplayText() {
            return switch (this) {
                case FATHERLESS -> "Yatim";
                case MOTHERLESS -> "Piatu";
                case BOTH_DECEASED -> "Yatim Piatu";
                case POOR -> "Dhuafa";
            };
        }
    }

    @Transient
    public String getOrphanTypeText() {
        return orphanType != null ? orphanType.getDisplayText() : "";
    }
}
