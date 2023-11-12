package com.z7.repository;

import com.z7.dto.InventoryResponse;
import com.z7.model.Invetory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<Invetory, Long> {
    List<Invetory> findBySkuCodeIn(List<String> skuCodes);

}
