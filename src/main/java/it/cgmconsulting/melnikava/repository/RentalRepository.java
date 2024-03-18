package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Rental;
import it.cgmconsulting.melnikava.entity.RentalId;
import it.cgmconsulting.melnikava.payload.response.CustomerStoreResponse;
import it.cgmconsulting.melnikava.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.melnikava.payload.response.FilmRentResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, RentalId> {

    @Query(value = "SELECT new it.cgmconsulting.melnikava.payload.response.CustomerStoreResponse(" +
            "UPPER(s.storeName), " +
            "COUNT(DISTINCT r.rentalId.customerId) AS totalCustomers) " +
            "FROM Rental r " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.storeId s " +
            "WHERE UPPER(s.storeName) = UPPER(:storeName) " +
            "GROUP BY UPPER(s.storeName)")
    CustomerStoreResponse getCustomersByStoreName(@Param("storeName") String storeName);

    @Query(value = "SELECT r FROM Rental r " +
            "JOIN r.rentalId.inventoryId i " +
            "WHERE i.inventoryId = :inventoryId " +
            "AND r.rentalReturn IS NULL")
    Optional<Rental> getRentalToUpdate(@Param("inventoryId") long inventoryId);

    @Query(value = "SELECT r.rentalId.inventoryId.inventoryId " +
            "FROM Rental r " +
            "WHERE r.rentalId.inventoryId.inventoryId IN :ids " +
            "AND r.rentalReturn IS NULL")
    List<Long> getUnavailableRentalIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT COUNT(r) " +
            "FROM Rental r " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.storeId s " +
            "WHERE s.storeId = :storeId " +
            "AND r.rentalId.rentalDate >= :dateStart " +
            "AND r.rentalId.rentalDate <= :dateEnd")
    long getCountByStoreAndDateRange(@Param("storeId") long storeId,
                                     @Param("dateStart") LocalDateTime dateStart,
                                     @Param("dateEnd") LocalDateTime dateEnd);

    @Query(value = "SELECT new it.cgmconsulting.melnikava.payload.response.FilmRentResponse(" +
            "f.filmId, " +
            "f.title, " +
            "s.storeName) " +
            "FROM Rental r " +
            "JOIN r.rentalId.customerId c " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.filmId f " +
            "JOIN i.storeId s " +
            "WHERE c.customerId = :customerId")
    List<FilmRentResponse> getFilmsByCustomer(@Param("customerId") long customerId);

    @Query(value = "SELECT new it.cgmconsulting.melnikava.payload.response.FilmMaxRentResponse(" +
            "f.filmId, " +
            "f.title, " +
            "COUNT(i.filmId) AS noleggiTotale) " +
            "FROM Rental r " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.filmId f " +
            "GROUP BY f.filmId " +
            "HAVING COUNT(i.filmId) = " +
            "(SELECT MAX(number.noleggiTotale) FROM " +
            "(SELECT COUNT(i2.filmId) AS noleggiTotale " +
            "FROM Rental r2 " +
            "JOIN r2.rentalId.inventoryId i2 " +
            "JOIN i2.filmId f2 " +
            "GROUP BY f2.filmId) AS number)")
    List<FilmMaxRentResponse> getFilmsByMaxRental();


}//
