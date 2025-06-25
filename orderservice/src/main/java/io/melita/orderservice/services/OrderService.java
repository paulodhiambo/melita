package io.melita.orderservice.services;

import io.melita.orderservice.models.requests.BaseRequest;
import io.melita.orderservice.models.requests.OrderRequest;
import io.melita.orderservice.models.responses.BaseResponse;

public interface OrderService {
    /**
     * Creates a new order with the specified product type and package name.
     *
     * @param request order request payload
     * @return the created order
     */
    BaseResponse<?> createOrder(BaseRequest<OrderRequest> request);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return the retrieved order
     */
    BaseResponse<?> getOrderById(Long orderId);
}
