package it.cgmconsulting.melnikava.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class FilmRequest {

    //I valori possono essere null per avere possibilità di lasciare tutto senza cambiamenti
    @Size(max = 100, message = "Title must not exceed {max} characters")
    private String title;
    @Size(max = 3000, message = "Description must not exceed {max} characters")
    private String description;
    @Min(value = 1900, message = "Year can't be earlier than {value}")
    private Short releaseYear;
    //private Long genreId; //1-8
    private Long genreId;
    private Long languageId; //1-6, 9

}
