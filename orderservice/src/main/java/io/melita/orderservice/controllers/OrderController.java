package io.melita.orderservice.controllers;

import io.melita.orderservice.models.requests.BaseRequest;
import io.melita.orderservice.models.requests.OrderRequest;
import io.melita.orderservice.models.responses.BaseResponse;
import io.melita.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> createOrder(@RequestBody @Valid BaseRequest<OrderRequest> orderRequest) {
        String correlationId = orderRequest.getMessageId();
        log.info("📥 [POST] Create Order request received. CorrelationId: {}, Payload: {}", correlationId, orderRequest);

        BaseResponse<?> response = orderService.createOrder(orderRequest);

        log.info("📤 Order created successfully. CorrelationId: {}, Response: {}", correlationId, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse<?>> getOrder(@PathVariable("orderId") Long orderId) {
        log.info("📥 [GET] Fetching order with ID: {}", orderId);

        BaseResponse<?> response = orderService.getOrderById(orderId);

        log.info("📤 Order retrieval successful. Order ID: {}, Response: {}", orderId, response);
        return ResponseEntity.ok(response);
    }
}
