package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Store;
import it.cgmconsulting.melnikava.repository.StoreRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }
    public Optional<Store> getByStoreId(long storeId) {
        return storeRepository.getByStoreId(storeId);
    }
    public boolean existsByStoreNameIgnoreCase(String storeName){
        return storeRepository.existsByStoreNameIgnoreCase(storeName);
    }
}
