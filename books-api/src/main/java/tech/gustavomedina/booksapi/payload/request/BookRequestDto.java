package tech.gustavomedina.booksapi.payload.request;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import tech.gustavomedina.booksapi.model.BookGenre;

import java.time.LocalDate;

public record BookRequestDto(
        @NotBlank(message = "The book's name must be a valid name.")
        @Size(max = 60)
        String name,

        @NotNull
        @Positive
        Short pages,

        @NotNull(message = "The book's genre must be valid.")
        BookGenre genre,

        @NotBlank(message = "The book's synopsis must be valid.")
        @Size(max = 255)
        String synopsis,

        @NotNull(message = "The book's date of publication must be valid.")
        @PastOrPresent(message = "The date is invalid.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate publicationDate,

        @NotBlank(message = "The book's ISBN must be valid.")
        @Size(max = 20)
        String isbn,

        @NotNull(message = "The book's author must be valid.")
        Integer authorId) {
}
