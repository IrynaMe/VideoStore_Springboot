package it.cgmconsulting.melnikava.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Store {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    @Column(nullable = false, unique = true, length = 60)
    private String storeName;

    public Store(String storeName) {
        this.storeName = storeName;
    }
}
