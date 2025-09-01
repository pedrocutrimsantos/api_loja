package br.com.pauloneto.loja.core.api;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private OffsetDateTime timestamp;
    private Integer status;

    public static <T> ApiResponse<T> ok(String msg, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(msg)
                .data(data)
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.OK.value())
                .build();
    }

    public static <T> ApiResponse<T> created(String msg, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(msg)
                .data(data)
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build();
    }
}
