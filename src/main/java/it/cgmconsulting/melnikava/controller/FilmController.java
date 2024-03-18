package it.cgmconsulting.melnikava.controller;

import it.cgmconsulting.melnikava.entity.Film;
import it.cgmconsulting.melnikava.entity.Genre;
import it.cgmconsulting.melnikava.entity.Language;
import it.cgmconsulting.melnikava.payload.request.FilmRequest;
import it.cgmconsulting.melnikava.payload.response.FilmLanguageResponse;
import it.cgmconsulting.melnikava.payload.response.FilmResponse;
import it.cgmconsulting.melnikava.service.FilmService;
import it.cgmconsulting.melnikava.service.FilmStaffService;
import it.cgmconsulting.melnikava.service.GenreService;
import it.cgmconsulting.melnikava.service.LanguageService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Validated
@RestController
@RequestMapping("film")
public class FilmController {
    private final FilmService filmService;
    private final LanguageService languageService;
    private final GenreService genreService;
    private final FilmStaffService filmStaffService;

    public FilmController(FilmService filmService, LanguageService languageService, GenreService genreService, FilmStaffService filmStaffService) {
        this.filmService = filmService;
        this.languageService = languageService;
        this.genreService = genreService;
        this.filmStaffService = filmStaffService;
    }

    @Transactional
    @PatchMapping("/update-film/{filmId}")
    public ResponseEntity<?> updateFilm(@PathVariable long filmId,
                                        @RequestBody @Valid FilmRequest request) {
        String newValues = "";
        Optional<Film> film = filmService.getFilmByFilmId(filmId);
        //Controllo se film è presente
        if (film.isEmpty())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        //TITLE opzionale aggiornamento + controllo se nuovo valore=vecchio valore
        if (request.getTitle() != null && !request.getTitle().equals(film.get().getTitle())) {
            // Controllo se title è già presente visto che è unique
            if (filmService.existsByTitleAndFilmIdNot(request.getTitle(), filmId))
                return new ResponseEntity<>("Changes non applied: title already present", HttpStatus.BAD_REQUEST);
            //controllo se new title è uguale al vecchio
            newValues += "title, ";
            film.get().setTitle(request.getTitle());
        }

        //DESCRIPTION opzionale aggiornamento + controllo se nuovo valore=vecchio valore
        if (request.getDescription() != null && !request.getDescription().equals(film.get().getDescription())) {
            newValues += "description, ";
            film.get().setDescription(request.getDescription());
        }

        //RELEASE YEAR opzionale aggiornamento + controllo se nuovo valore=vecchio valore
        if (request.getReleaseYear() != null && request.getReleaseYear() != film.get().getReleaseYear()) {
            //controllo se release year non è in futuro
            if (request.getReleaseYear() > Year.now().getValue())
                return new ResponseEntity<>("Changes non applied: release year must be in range from 1900 to current year", HttpStatus.BAD_REQUEST);
            newValues += "release year, ";
            film.get().setReleaseYear(request.getReleaseYear());
        }

        //GENRE ID opzionale aggiornamento + controllo se nuovo valore=vecchio valore
        if (request.getGenreId() != null && !request.getGenreId().equals(film.get().getGenreId().getGenreId())) {
            Optional<Genre> genre = genreService.getByGenreId(request.getGenreId());
            //se genre è presente
            if (genre.isEmpty())
                return new ResponseEntity<>("Changes non applied: genre not found", HttpStatus.BAD_REQUEST);
            newValues += "genre id, ";
            film.get().setGenreId(genre.get());
        }

        //LANGUAGE ID opzionale aggiornamento + controllo se nuovo valore=vecchio valore
        if (request.getLanguageId() != null && !request.getLanguageId().equals(film.get().getLanguageId().getLanguageId())) {
            Optional<Language> language = languageService.getByLanguageId(request.getLanguageId());
            //se genre è presente
            if (language.isEmpty())
                return new ResponseEntity<>("Changes non applied: language not found", HttpStatus.BAD_REQUEST);
            newValues += "language id, ";
            film.get().setLanguageId(language.get());
        }
        newValues = newValues.length() > 0 ? newValues.substring(0, newValues.length() - 2) : "no values";
        return new ResponseEntity<>("You have updated " + newValues, HttpStatus.OK);
    }


    @GetMapping("/find-films-by-language/{languageId}")
    public ResponseEntity<?> findFilmsByLanguage(@PathVariable long languageId) {
        //controllo se language id esiste
        if (languageService.getByLanguageId(languageId).isEmpty())
            return new ResponseEntity<>("Language not found", HttpStatus.BAD_REQUEST);
        List<FilmLanguageResponse> list = filmService.getFilmsByLanguageId(languageId);
        //controllo se ci sono film con scelto language
        if (list.isEmpty())
            return new ResponseEntity<>("Films not found, try another language id", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> findByActors(@RequestParam Set<Long> actorsIds) {
        //controllo se actorsIds esistono
        String message = "";
        for (Long id : actorsIds) {
            if (!filmStaffService.existsById(id))
                message += "No matches found in database for input: " + id + "\n";
        }
        //controllo se nel actorsIds ci sono solo ruoli di attore
        Set<Long> allActorsIds = filmStaffService.getActorsIds();
        if (!allActorsIds.isEmpty()) {
            for (Long actorId : actorsIds) {
                if (!allActorsIds.contains(actorId) && !message.contains(" " + actorId.toString() + "\n"))
                    message += "No matches with role 'ACTOR' found for your input: " + actorId + "\n";
            }
        }
        if (message.length() > 0)
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        //ricevo FilmResponse
        List<FilmResponse> list = filmStaffService.getFilmsByActors(actorsIds, actorsIds.size());
        //controllo se esiste un film con tutti attori predefiniti
        if (list.isEmpty())
            return new ResponseEntity<>("No film found matching the selected actors", HttpStatus.OK);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}//
