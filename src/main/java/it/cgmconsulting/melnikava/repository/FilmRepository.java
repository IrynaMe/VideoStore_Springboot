package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Film;
import it.cgmconsulting.melnikava.payload.response.FilmLanguageResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FilmRepository extends JpaRepository<Film, Long> {
    boolean existsByTitleAndFilmIdNot(String title, long filmId);

    @Query(value = "SELECT new it.cgmconsulting.melnikava.payload.response.FilmLanguageResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "l.languageName) FROM Film f " +
            "JOIN f.languageId l " +
            "WHERE l.languageId = :languageId")
    List<FilmLanguageResponse> getFilmsByLanguageId(@Param("languageId") long languageId);

    @Query(value = "SELECT f FROM Film f WHERE f.filmId = :filmId")
    Optional<Film> getFilmByFilmId(@Param("filmId") long filmId);


}//
