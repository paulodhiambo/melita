package io.melita.orderservice.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Customer customer;
    private Address installationAddress;
    private InstallationSchedule  installationSlot;
    private List<Product> products;
}
