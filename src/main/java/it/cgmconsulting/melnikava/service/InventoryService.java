package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Inventory;
import it.cgmconsulting.melnikava.payload.response.FilmRentableResponse;
import it.cgmconsulting.melnikava.repository.InventoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void save(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

   public List<Long> getIds(long filmId, long storeId) {
        return inventoryRepository.getIds(filmId,storeId);
    }
    public Inventory getByInventoryId(long inventoryId){
        return inventoryRepository.getByInventoryId(inventoryId);
    }
    public List<FilmRentableResponse> getRentableFilmsByTitle(String title){
        return inventoryRepository.getRentableFilmsByTitle(title);
    }
}
