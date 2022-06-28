package com.iniestar.mark1.config.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ServerErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;

    public static ResponseEntity<ServerErrorResponse> toResponseEntity(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ServerErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }
}
