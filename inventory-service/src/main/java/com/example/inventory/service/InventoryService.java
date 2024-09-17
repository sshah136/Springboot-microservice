package com.example.inventory.service;

import com.example.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku, Integer quantity){

        return inventoryRepository.existsBySkuAndQuantityIsGreaterThanEqual(sku,quantity);
    }
}
