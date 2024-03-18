package it.cgmconsulting.melnikava.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmRentableResponse {
    private String title;
    private String storeName;
    private long numberTotal;
    private long numberAvailable;
}
