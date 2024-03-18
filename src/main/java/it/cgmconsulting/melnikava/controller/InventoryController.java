package it.cgmconsulting.melnikava.controller;

import it.cgmconsulting.melnikava.entity.Film;
import it.cgmconsulting.melnikava.entity.Inventory;
import it.cgmconsulting.melnikava.entity.Store;
import it.cgmconsulting.melnikava.payload.response.FilmRentableResponse;
import it.cgmconsulting.melnikava.service.FilmService;
import it.cgmconsulting.melnikava.service.InventoryService;
import it.cgmconsulting.melnikava.service.StoreService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    private final StoreService storeService;
    private final FilmService filmService;

    public InventoryController(InventoryService inventoryService, StoreService storeService, FilmService filmService) {
        this.inventoryService = inventoryService;
        this.storeService = storeService;
        this.filmService = filmService;
    }


    @PutMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilmToStore(@PathVariable long storeId, @PathVariable long filmId) {
        Optional<Store> store = storeService.getByStoreId(storeId);
        //controllo se store esiste
        if (store.isEmpty())
            return new ResponseEntity<>("Film not added: store not found", HttpStatus.BAD_REQUEST);
        Optional<Film> film = filmService.getFilmByFilmId(filmId);
        //controllo se film esiste
        if (film.isEmpty())
            return new ResponseEntity<>("Film not found", HttpStatus.BAD_REQUEST);
        inventoryService.save(new Inventory(store.get(), film.get()));
        return new ResponseEntity<>("Film added to store", HttpStatus.OK);
    }

    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> findRentableFilms(@RequestParam String title){
        List<FilmRentableResponse> response=inventoryService.getRentableFilmsByTitle(title);
        //controllo se titolo esiste
        if (response.isEmpty())
            return new ResponseEntity<>("The title not found", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}//
