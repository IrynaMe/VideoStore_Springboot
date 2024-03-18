package it.cgmconsulting.melnikava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Film {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT", length = 3000)
    private String description;
    @NonNull
    private short releaseYear;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language languageId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genreId;
    public Film(String title, String description, short releaseYear, Language languageId, Genre genreId) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.genreId = genreId;
    }
}
