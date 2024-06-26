package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "inventory_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "inventoryType", cascade = CascadeType.ALL)
    private Set<Inventory> inventories;
}
