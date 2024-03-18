package it.cgmconsulting.melnikava.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FilmStaff {
    @EmbeddedId
    private FilmStaffId filmStaffId;

}
