package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.payload.response.FilmResponse;
import it.cgmconsulting.melnikava.repository.FilmStaffRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FilmStaffService {
    private final FilmStaffRepository filmStaffRepository;

    public FilmStaffService(FilmStaffRepository filmStaffRepository) {
        this.filmStaffRepository = filmStaffRepository;
    }

    public Set<Long> getActorsIds() {
        return filmStaffRepository.getActorsIds();
    }

    public List<FilmResponse> getFilmsByActors(Set<Long> actorsIds, int number) {
        return filmStaffRepository.getFilmsByActors(actorsIds, number);
    }

    public boolean existsById(long staffId) {
        return filmStaffRepository.existsByFilmStaffIdStaffIdStaffId(staffId);
    }
}
