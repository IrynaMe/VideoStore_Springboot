package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Genre;
import it.cgmconsulting.melnikava.repository.GenreRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> getByGenreId(long genreId) {
        return genreRepository.getByGenreId(genreId);
    }
/*    public Optional<Genre> getByGenreName(String genreName){
        return genreRepository.getByGenreName(genreName);
    }*/
/*    public List<String> getAllGenres(){
        return genreRepository.getAllGenres();
    }*/
/*public List<Genre> getAllGenres(){
    return genreRepository.getAllGenres();
}*/
}
