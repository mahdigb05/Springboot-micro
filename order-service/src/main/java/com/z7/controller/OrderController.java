package com.z7.controller;

import com.z7.dto.OrderRequest;
import com.z7.model.Order;
import com.z7.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
    @TimeLimiter(name = "inventory")
    @Retry(name="inventory")
    public CompletableFuture<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() ->
                orderService.placeOrder(orderRequest)
        );
    }

    private CompletableFuture<Order> placeOrderFallback(OrderRequest orderRequest, RuntimeException exception) {
        throw new RuntimeException("couldn't place the order please try again later");
    }

}
