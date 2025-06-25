package io.melita.orderservice.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
