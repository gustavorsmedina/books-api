package tech.gustavomedina.booksapi.payload.request;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AuthorRequestDto(
        @NotBlank(message = "The author's name must be a valid name.")
        @Size(max = 60)
        String name,

        @NotNull(message = "The author's date of birth must be valid.")
        @Past(message = "The date is invalid.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @NotBlank(message = "The author's biography must be valid.")
        @Size(max = 255)
        String biography
) {
}
