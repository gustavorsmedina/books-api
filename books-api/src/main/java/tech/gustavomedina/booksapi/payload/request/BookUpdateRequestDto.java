package tech.gustavomedina.booksapi.payload.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import tech.gustavomedina.booksapi.model.BookGenre;

import java.time.LocalDate;

public record BookUpdateRequestDto(
        @Size(max = 60)
        String name,

        @Positive
        Short pages,

        BookGenre genre,

        @Size(max = 255)
        String synopsis,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate publicationDate,

        String isbn,
        Integer authorId) {
}
