package it.cgmconsulting.melnikava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Genre {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long genreId;

    @Column(nullable = false, unique = true, length = 30)
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

}
