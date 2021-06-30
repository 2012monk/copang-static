package com.example.copangstatic.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ResponseMessage<T> {

    private String message;

    private String code;

    private T data;

    public static <T> ResponseMessage<T> of(T data) {
        return ResponseMessage.<T>builder()
            .message("success")
            .data(data)
            .code("uploaded")
            .build();
    }

}
