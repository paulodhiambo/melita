package io.melita.orderservice.models.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseResponse<T> {
    private String messageId;
    private String message;
    private T data;
}
