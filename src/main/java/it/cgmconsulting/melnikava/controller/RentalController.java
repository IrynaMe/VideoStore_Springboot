package it.cgmconsulting.melnikava.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import it.cgmconsulting.melnikava.entity.Customer;
import it.cgmconsulting.melnikava.entity.Inventory;
import it.cgmconsulting.melnikava.entity.Rental;
import it.cgmconsulting.melnikava.entity.RentalId;

import it.cgmconsulting.melnikava.payload.response.CustomerStoreResponse;
import it.cgmconsulting.melnikava.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.melnikava.payload.response.FilmRentResponse;

import it.cgmconsulting.melnikava.service.CustomerService;
import it.cgmconsulting.melnikava.service.InventoryService;
import it.cgmconsulting.melnikava.service.RentalService;
import it.cgmconsulting.melnikava.service.StoreService;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rental")
@Validated
public class RentalController {
    private final RentalService rentalService;
    private final StoreService storeService;
    private final InventoryService inventoryService;
    private final CustomerService customerService;

    public RentalController(RentalService rentalService, StoreService storeService, InventoryService inventoryService, CustomerService customerService) {
        this.rentalService = rentalService;
        this.storeService = storeService;
        this.inventoryService = inventoryService;
        this.customerService = customerService;
    }

    @GetMapping("/count-customers-by-store/{storeName}")
    public ResponseEntity<?> countCustomers(@PathVariable String storeName) {
        //controllo se store esiste
        if (!storeService.existsByStoreNameIgnoreCase(storeName))
            return new ResponseEntity<>("Store not found", HttpStatus.BAD_REQUEST);
        CustomerStoreResponse response = rentalService.getCustomersByStoreName(storeName);
        //se non ci sono customers nel store
        if (response == null)
            return new ResponseEntity<>(storeName + " has no customers yet", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Per gestire add/update obbligatorio scegliere "add" or "update" per parametro actionRequired
    // Per update serve inventory Id, per add serve film id, store id, customer id
    @Transactional
    @PutMapping("/add-update-rental")
    public ResponseEntity<?> addUpdateRental(@RequestParam(required = true) @Schema(allowableValues = {"update","add"}) String actionRequired,
                                             @RequestParam(required = false) Long inventoryId,
                                             @RequestParam(required = false) Long customerId,
                                             @RequestParam(required = false) Long filmId,
                                             @RequestParam(required = false) Long storeId) {
        //UPDATE
        if (actionRequired.equals("update")) {
            String message="";
            //per update inventoryId obbligatorio
            if (inventoryId == null)
                return new ResponseEntity<>("For update you must insert inventory id", HttpStatus.BAD_REQUEST);
            Optional<Rental> rental = rentalService.getRentalToUpdate(inventoryId);
            //controllo se ci sono inserite altri valori non obbligatori
            if (customerId!=null||filmId!=null||storeId!=null)
                message=". Other values from input were not considered";
            //controllo se rental esiste
            if (rental.isEmpty())
                return new ResponseEntity<>("Rental not found", HttpStatus.BAD_REQUEST);
            rental.get().setRentalReturn(LocalDateTime.now());
            return new ResponseEntity<>("Rental updated for inventory: "+inventoryId+message, HttpStatus.OK);
        } else {

            //ADD
            //controllo parametri obbligatori per ADD
            if (customerId == null || filmId == null || storeId == null)
                return new ResponseEntity<>("To add rental you must insert all values: customer id, film id, store id", HttpStatus.BAD_REQUEST);
            //controllo se c'e inventory id
            String message="";
            if (inventoryId!=null)
                message=". Inventory id from input was not considered.";
            //controllo se c'è film in store
            List<Long> inventoryIds = inventoryService.getIds(filmId, storeId);
            if (inventoryIds.isEmpty())
                return new ResponseEntity<>("Film not present in the store", HttpStatus.NOT_FOUND);

            //controllo se film non è gia noleggiato
            List<Long> unavailableIds = rentalService.getUnavailableRentalIds(inventoryIds);
            if (inventoryIds.size() <= unavailableIds.size())
                return new ResponseEntity<>("Film is currently not available in the store", HttpStatus.OK);

            //controllo se film non è stato mai noleggiato ma presente nel negozio
            List<Long> availableIds = null;
            availableIds = inventoryIds.stream().filter(id -> !unavailableIds.contains(id)).collect(Collectors.toList());

            Inventory inventory = inventoryService.getByInventoryId(availableIds.get(0));
            Customer customer = customerService.getCustomerByCustomerId(customerId).get();
            rentalService.save(new Rental(new RentalId(customer, inventory)));
            return new ResponseEntity<>("Rental added"+message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<?> countRentals(@PathVariable long storeId,
                                          @RequestParam String dateStart,
                                          @RequestParam String dateEnd) {
        // Controllo se storeId esiste
        if (storeService.getByStoreId(storeId).isEmpty())
            return new ResponseEntity<>("Store not found", HttpStatus.BAD_REQUEST);

        //controllo se dateStart and dateEnd hanno il formato coretto e trasformo in LocalDateTime
        try {
            LocalDateTime startDateTime = LocalDate.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            LocalDateTime endDateTime = LocalDate.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.MAX);
            //controllo se start date è in passato e primo di end date
            //non c'è controllo per end date in futuro siccome non influisce sul risultato
            String message="";
            if (startDateTime.isAfter(LocalDateTime.now()))
                message+="Start date must be in the past. ";
            if (startDateTime.isAfter(endDateTime))
                message+="End date must be later than start date. ";
            if (message.length()>0)
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            long rentals = rentalService.getCountByStoreAndDateRange(storeId, startDateTime, endDateTime);
            return new ResponseEntity<>(rentals, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Dates must be in format: yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> findFilmsByCustomer(@PathParam("customerId") long customerId) {
        //controllo se customer id esiste
        if (customerService.getCustomerByCustomerId(customerId).isEmpty())
            return new ResponseEntity<>("Customer not found", HttpStatus.BAD_REQUEST);
        List<FilmRentResponse> filmResponses = rentalService.getFilmsByCustomer(customerId);
        //controllo se il customer ha fatto rentals
        if (filmResponses.isEmpty())
            return new ResponseEntity<>("Customer haven't got any rentals yet", HttpStatus.OK);

        return new ResponseEntity<>(filmResponses, HttpStatus.OK);
    }

    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<?> findFilmMaxRental() {
        List<FilmMaxRentResponse> list = rentalService.getFilmsByMaxRental();
        //controllo se film è stato noleggiato
        if (list.isEmpty())
            return new ResponseEntity<>("No film has been rented yet", HttpStatus.OK);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}//
