package com.iniestar.mark1.config.response;

import com.iniestar.mark1.controller.ext.ApiResult;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final ApiResult apiResult;

    public static ResponseEntity<ApiErrorResponse> toResponseEntity(ApiResult apiResult) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .apiResult(apiResult)
                        .build()
                );
    }
}
