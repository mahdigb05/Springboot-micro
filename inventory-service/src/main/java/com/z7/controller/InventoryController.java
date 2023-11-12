package com.z7.controller;


import com.z7.dto.InventoryResponse;
import com.z7.dto.InventoryRequest;
import com.z7.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/invetory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInstock(
            @RequestBody InventoryRequest inventoryRequest
            ){
        return inventoryService.isInStock(inventoryRequest);
    }

}
