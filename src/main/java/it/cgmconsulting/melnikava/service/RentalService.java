package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Rental;
import it.cgmconsulting.melnikava.payload.response.CustomerStoreResponse;
import it.cgmconsulting.melnikava.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.melnikava.payload.response.FilmRentResponse;
import it.cgmconsulting.melnikava.repository.RentalRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public CustomerStoreResponse getCustomersByStoreName(String storeName) {
        return rentalRepository.getCustomersByStoreName(storeName);
    }

    public Optional<Rental> getRentalToUpdate(long inventoryId) {
        return rentalRepository.getRentalToUpdate(inventoryId);
    }

    public List<Long> getUnavailableRentalIds(List<Long> ids) {
        return rentalRepository.getUnavailableRentalIds(ids);
    }

    public void save(Rental rental) {
        rentalRepository.save(rental);
    }

    public long getCountByStoreAndDateRange(long storeId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return rentalRepository.getCountByStoreAndDateRange(storeId, dateStart, dateEnd);
    }

    public List<FilmRentResponse> getFilmsByCustomer(long customerId){
        return rentalRepository.getFilmsByCustomer(customerId);
    }
    public List<FilmMaxRentResponse> getFilmsByMaxRental(){
        return rentalRepository.getFilmsByMaxRental();
    }


}//
