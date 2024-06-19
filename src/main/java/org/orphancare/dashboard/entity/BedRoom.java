package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "bedRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles;
}
