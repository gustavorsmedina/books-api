package tech.gustavomedina.booksapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.gustavomedina.booksapi.payload.request.BookRequestDto;
import tech.gustavomedina.booksapi.payload.response.BookResponseDto;
import tech.gustavomedina.booksapi.payload.request.BookUpdateRequestDto;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto bookRequestDto);
    Page<BookResponseDto> getAllBooks(Pageable pageable);
    BookResponseDto getBookById(Integer id);
    BookResponseDto updateBookById(Integer id, BookUpdateRequestDto bookUpdateRequestDto);
    void deleteBookById(Integer id);
    Page<BookResponseDto> getBooksByName(Pageable pageable, String name);
    Page<BookResponseDto> getBooksByGenre(Pageable pageable, String genre);
}
