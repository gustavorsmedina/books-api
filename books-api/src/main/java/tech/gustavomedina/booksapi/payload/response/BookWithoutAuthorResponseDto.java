package tech.gustavomedina.booksapi.payload.response;

import tech.gustavomedina.booksapi.model.BookGenre;

import java.time.LocalDate;

public record BookWithoutAuthorResponseDto(Integer id, String name, Short pages, BookGenre genre, String synopsis, LocalDate publicationDate, String isbn) {
}
