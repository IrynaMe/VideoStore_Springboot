package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT s FROM Store s WHERE s.storeId = :storeId")
    Optional<Store> getByStoreId(@Param("storeId") long storeId);
    boolean existsByStoreNameIgnoreCase(@Param("storeName") String storeName);
}
