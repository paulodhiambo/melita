package io.melita.orderservice.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequest<T> {
    private String messageId;
    private T data;
}
