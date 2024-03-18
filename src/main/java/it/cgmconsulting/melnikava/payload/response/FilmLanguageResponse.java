package it.cgmconsulting.melnikava.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmLanguageResponse {
    private long filmId;
    private String title;
    private String description;
    private short releaseYear;
    private String languageName;
}
