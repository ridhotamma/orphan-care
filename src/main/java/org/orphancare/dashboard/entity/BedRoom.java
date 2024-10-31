package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bedrooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Size(max = 255)
    @Column
    private String name;

    @ManyToOne()
    @JoinColumn(name = "bedroom_type_id", nullable = false)
    private BedRoomType bedRoomType;

    @OneToMany(mappedBy = "bedRoom")
    private List<Profile> profiles = new ArrayList<>();

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

    public void setProfiles(List<Profile> profiles) {
        if (this.profiles != null) {
            this.profiles.forEach(profile -> profile.setBedRoom(null));
        }

        this.profiles = profiles;

        if (profiles != null) {
            profiles.forEach(profile -> profile.setBedRoom(this));
        }
    }
}
