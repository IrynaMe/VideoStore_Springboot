package it.cgmconsulting.melnikava.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmMaxRentResponse {
    private long film_id;
    private String title;
    private long noleggiTotale;
}
