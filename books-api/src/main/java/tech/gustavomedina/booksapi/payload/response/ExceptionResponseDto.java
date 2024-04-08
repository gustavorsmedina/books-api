package tech.gustavomedina.booksapi.payload.response;

import java.time.LocalDateTime;

public record ExceptionResponseDto(LocalDateTime date, String status, String message, String path) {
}
