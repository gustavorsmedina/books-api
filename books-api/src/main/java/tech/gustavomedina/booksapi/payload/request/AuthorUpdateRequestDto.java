package tech.gustavomedina.booksapi.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AuthorUpdateRequestDto(
        @Size(max = 60)
        String name,

        @Past(message = "The date is invalid.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @Size(max = 255)
        String biography) {
}
