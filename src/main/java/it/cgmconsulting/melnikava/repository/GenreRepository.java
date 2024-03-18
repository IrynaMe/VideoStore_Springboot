package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

   @Query(value = "SELECT g FROM Genre g WHERE genreId = :genreId")
    Optional<Genre> getByGenreId(@Param("genreId") long genreId);


}
