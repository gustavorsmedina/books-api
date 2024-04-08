package tech.gustavomedina.booksapi.payload.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ExceptionResponseWithFieldsDto(LocalDateTime date, String status, String message, String path, Map<String, String> errors) {
}
