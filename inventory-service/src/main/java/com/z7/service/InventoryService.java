package com.z7.service;

import com.z7.dto.InventoryRequest;
import com.z7.dto.InventoryResponse;
import com.z7.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(InventoryRequest inventoryRequest) {
        log.info("checking inventory");
        return inventoryRepository.
                findBySkuCodeIn(inventoryRequest.getProductIdsList()).stream().map(inventory -> InventoryResponse.builder().isInstock(inventory.getQuantity() > 0).skuCode(inventory.getSkuCode()).build()).toList();

    }

}
