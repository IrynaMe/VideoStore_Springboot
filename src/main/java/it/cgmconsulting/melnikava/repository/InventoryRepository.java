package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Inventory;
import it.cgmconsulting.melnikava.payload.response.FilmRentableResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query(value = "SELECT i.inventoryId " +
            "FROM Inventory i " +
            "JOIN i.filmId f " +
            "JOIN i.storeId s " +
            "WHERE f.filmId = :filmId AND s.storeId = :storeId")
    List<Long> getIds(@Param("filmId") long filmId, @Param("storeId") long storeId);

    @Query(value = "SELECT i FROM Inventory i WHERE i.inventoryId = :inventoryId")
    Inventory getByInventoryId(@Param("inventoryId") long inventoryId);
    @Query("SELECT new it.cgmconsulting.melnikava.payload.response.FilmRentableResponse(" +
            "UPPER(f.title), " +
            "s.storeName, " +
            "COUNT(i), " +
            "(COUNT(i) - COUNT(r))) " +
            "FROM Inventory i " +
            "JOIN i.filmId f " +
            "JOIN i.storeId s " +
            "LEFT JOIN Rental r ON r.rentalId.inventoryId = i " +
            "AND r.rentalReturn IS NULL " +
            "WHERE UPPER(f.title) = UPPER(:title) " +
            "GROUP BY UPPER(f.title), s.storeName")
    List<FilmRentableResponse> getRentableFilmsByTitle(@Param("title") String title);


}//
