package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Film;
import it.cgmconsulting.melnikava.payload.response.FilmLanguageResponse;
import it.cgmconsulting.melnikava.repository.FilmRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public boolean existsByTitleAndFilmIdNot(String title, long filmId) {
        return filmRepository.existsByTitleAndFilmIdNot(title, filmId);
    }

    public List<FilmLanguageResponse> getFilmsByLanguageId(long languageId) {
        return filmRepository.getFilmsByLanguageId(languageId);
    }

    public Optional<Film> getFilmByFilmId(long filmId) {
        return filmRepository.getFilmByFilmId(filmId);
    }


}//