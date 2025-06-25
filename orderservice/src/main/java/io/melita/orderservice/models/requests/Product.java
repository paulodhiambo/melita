package io.melita.orderservice.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String productType;
    private String packageName;
}
