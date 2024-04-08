package tech.gustavomedina.booksapi.payload.response;

import java.time.LocalDate;
import java.util.List;

public record AuthorResponseDto(Integer id, String name, LocalDate birthDate, String biography) {
}
