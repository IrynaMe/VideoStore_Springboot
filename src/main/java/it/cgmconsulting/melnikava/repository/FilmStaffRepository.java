package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.FilmStaff;
import it.cgmconsulting.melnikava.entity.FilmStaffId;
import it.cgmconsulting.melnikava.payload.response.FilmResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FilmStaffRepository extends JpaRepository<FilmStaff, FilmStaffId> {
    boolean existsByFilmStaffIdStaffIdStaffId(long staffId);
    @Query(value = "SELECT fs.filmStaffId.staffId.staffId " +
            "FROM FilmStaff fs " +
            "JOIN fs.filmStaffId.roleId r " +
            "WHERE r.roleName = 'ACTOR'")
    Set<Long> getActorsIds();


    @Query("SELECT new it.cgmconsulting.melnikava.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title) " +
            "FROM FilmStaff fs " +
            "JOIN fs.filmStaffId.filmId f " +
            "WHERE fs.filmStaffId.staffId.staffId IN :actorsIds " +
            "GROUP BY f.filmId, f.title " +
            "HAVING COUNT(DISTINCT fs.filmStaffId.staffId.staffId) = :number " +
            "ORDER BY f.title")
    List<FilmResponse> getFilmsByActors(@Param("actorsIds") Set<Long> actorsIds,
                                        @Param("number") int number);



}
