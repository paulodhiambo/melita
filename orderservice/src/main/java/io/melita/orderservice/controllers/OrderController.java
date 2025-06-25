package io.melita.orderservice.controllers;

import io.melita.orderservice.models.requests.BaseRequest;
import io.melita.orderservice.models.requests.OrderRequest;
import io.melita.orderservice.models.responses.BaseResponse;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {
    @PostMapping
    ResponseEntity<BaseResponse<?>> createOrder(@RequestBody @Valid BaseRequest<OrderRequest> orderRequest) {
        BaseResponse<?> response = new BaseResponse<>();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
