package com.z7.eventHandler;

import com.z7.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(groupId = "notification-group", topics = "orders")
@Slf4j
public class OrderPlacedEventHandler {


    @KafkaHandler(isDefault = true)
    public void orderPlacedHandler(OrderPlacedEvent orderPacedEvent){
        log.info("order with number {} was placed successfully", orderPacedEvent.getOrderNumber());
    }

}
