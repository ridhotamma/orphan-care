package org.orphancare.dashboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;


@Entity
@Table(name = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public BigInteger quantity;

    @ManyToOne
    @JoinColumn(name = "inventory_type_id", nullable = false)
    InventoryType inventoryType;
}
