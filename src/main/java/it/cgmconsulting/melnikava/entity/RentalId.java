package it.cgmconsulting.melnikava.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RentalId implements Serializable {
    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId;

    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventoryId;

    @EqualsAndHashCode.Include
    @Column(name = "rental_date", nullable = false, updatable = false)
    private LocalDateTime rentalDate;

    public RentalId(Customer customerId, Inventory inventoryId) {
        this.customerId = customerId;
        this.inventoryId = inventoryId;
        this.rentalDate = LocalDateTime.now();
    }
}
