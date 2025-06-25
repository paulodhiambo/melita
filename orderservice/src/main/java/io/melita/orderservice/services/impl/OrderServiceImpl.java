package io.melita.orderservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.melita.orderservice.domain.Address;
import io.melita.orderservice.domain.CustomerDetails;
import io.melita.orderservice.domain.Order;
import io.melita.orderservice.domain.ProductOrder;
import io.melita.orderservice.models.requests.BaseRequest;
import io.melita.orderservice.models.requests.OrderRequest;
import io.melita.orderservice.models.responses.BaseResponse;
import io.melita.orderservice.repository.OrderRepository;
import io.melita.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.order-topic}")
    private String orderTopic;

    @Override
    public BaseResponse<?> createOrder(BaseRequest<OrderRequest> request) {
        String correlationId = request.getMessageId() != null ? request.getMessageId() : UUID.randomUUID().toString();
        log.info("Received order creation request. CorrelationId: {}", correlationId);

        try {
            OrderRequest orderRequest = request.getData();

            CustomerDetails customer = CustomerDetails.builder()
                    .firstName(orderRequest.getCustomer().getFirstName())
                    .lastName(orderRequest.getCustomer().getLastName())
                    .email(orderRequest.getCustomer().getEmail())
                    .phone(orderRequest.getCustomer().getPhone())
                    .build();

            Address address = Address.builder()
                    .line1(orderRequest.getInstallationAddress().getStreet())
                    .city(orderRequest.getInstallationAddress().getCity())
                    .postalCode(orderRequest.getInstallationAddress().getPostalCode())
                    .build();

            Order order = Order.builder()
                    .customer(customer)
                    .installationAddress(address)
                    .preferredInstallationDate(orderRequest.getInstallationSlot().getDate())
                    .timeSlot(orderRequest.getInstallationSlot().getTimeSlot())
                    .build();

            List<ProductOrder> productOrders = orderRequest.getProducts().stream()
                    .map(p -> ProductOrder.builder()
                            .productType(p.getProductType())
                            .packageName(p.getPackageName())
                            .order(order) // maintain bidirectional relationship
                            .build())
                    .toList();

            order.setProducts(productOrders);

            Order savedOrder = orderRepository.save(order);
            log.info("Order persisted successfully. OrderId: {}", savedOrder.getId());

            String orderJson = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(orderTopic, orderJson).addCallback(
                result -> log.info("Order event published to Kafka. CorrelationId: {}, Partition: {}, Offset: {}",
                        correlationId,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()),
                ex -> log.error("Failed to publish order event to Kafka. CorrelationId: {}", correlationId, ex)
            );

            return BaseResponse.builder()
                    .messageId(correlationId)
                    .message("Order created successfully")
                    .data(null)
                    .build();

        } catch (Exception e) {
            log.error("Error while processing order. CorrelationId: {}", correlationId, e);
            return BaseResponse.builder()
                    .messageId(correlationId)
                    .message("Failed to create order: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public BaseResponse<?> getOrderById(Long orderId) {
        log.info("Fetching order by ID: {}", orderId);
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        return orderOpt
                .map(order -> {
                    log.info("Order found. ID: {}", order.getId());
                    return BaseResponse.builder()
                            .data(order)
                            .message("Order retrieved successfully")
                            .messageId(UUID.randomUUID().toString())
                            .build();
                })
                .orElseGet(() -> {
                    log.warn("Order not found with ID: {}", orderId);
                    return BaseResponse.builder()
                            .data(null)
                            .messageId(UUID.randomUUID().toString())
                            .message("Order with provided ID not found")
                            .build();
                });
    }
}
