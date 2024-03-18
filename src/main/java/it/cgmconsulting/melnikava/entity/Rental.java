package it.cgmconsulting.melnikava.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Rental {
    @EmbeddedId
    private RentalId rentalId;
    private LocalDateTime rentalReturn;

    public Rental(RentalId rentalId) {
        this.rentalId = rentalId;
        this.rentalReturn=null;
    }
}
