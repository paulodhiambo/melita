package io.melita.orderservice.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String street;
    private String city;
    private String postalCode;
}
