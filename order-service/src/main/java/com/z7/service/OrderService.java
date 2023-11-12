package com.z7.service;

import com.z7.dto.InventoryRequest;
import com.z7.dto.InventoryResponse;
import com.z7.dto.OrderLineItemsDto;
import com.z7.dto.OrderRequest;
import com.z7.model.Order;
import com.z7.model.OrderLineItem;
import com.z7.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public Order placeOrder(OrderRequest orderRequest) {
        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItems().stream().map(this::mapOrderLineItemsToDto).toList();
        Order order = Order.builder().orderLineItemsList(orderLineItems).orderNumber(UUID.randomUUID().toString()).build();

        //in order to place an order all products in the ordr should be available in stock
        // we need to reach to the inventory service and check if the products are in stock
        // and the quantity covers the current request

        InventoryRequest inventoryRequest = InventoryRequest.builder().productIdsList(order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList()).build();

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InventoryResponse[]> res =  template.postForEntity("/api/inventory", inventoryRequest, InventoryResponse[].class);
        InventoryResponse[] responseBody = res.getBody();

        boolean allProductsInStock = Arrays.stream(responseBody).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock){
            orderRepository.save(order);
            return order;
        }else throw new IllegalArgumentException("Product is not in stock, please try again later");
    }

    private OrderLineItem mapOrderLineItemsToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItem.builder()
                .id(orderLineItemsDto.getId())
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
