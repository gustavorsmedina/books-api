package tech.gustavomedina.booksapi.controller.impl;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gustavomedina.booksapi.controller.BookController;
import tech.gustavomedina.booksapi.payload.request.BookRequestDto;
import tech.gustavomedina.booksapi.payload.response.BookResponseDto;
import tech.gustavomedina.booksapi.payload.request.BookUpdateRequestDto;
import tech.gustavomedina.booksapi.service.impl.BookServiceImpl;

import java.net.URI;

@RestController
@RequestMapping("/v1/books")
public class BookControllerImpl implements BookController {

    private BookServiceImpl bookService;

    public BookControllerImpl(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto){

        var book = bookService.createBook(bookRequestDto);

        return ResponseEntity.created(URI.create("/v1/books/" + book.id().toString())).body(book);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookResponseDto>> getAllBooks(@PageableDefault(sort = {"name"}) Pageable pageable){

        var books = bookService.getAllBooks(pageable);

        return ResponseEntity.ok(books);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Integer id){

        var book = bookService.getBookById(id);

        return ResponseEntity.ok(book);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> updateAuthorById(@PathVariable("id") Integer id, @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto){

        var book = bookService.updateBookById(id, bookUpdateRequestDto);

        return ResponseEntity.ok(book);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("id") Integer id){

        bookService.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/search", params = "name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookResponseDto>> getBooksByName(@PageableDefault(sort = {"name"}) Pageable pageable, @RequestParam("name") String name){

        var books = bookService.getBooksByName(pageable, name);

        return ResponseEntity.ok(books);
    }

    @GetMapping(path = "/genre/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookResponseDto>> getBooksByGenre(@PageableDefault(sort = {"name"}) Pageable pageable, @PathVariable("genre") String genre){

        var books = bookService.getBooksByGenre(pageable, genre);

        return ResponseEntity.ok(books);
    }

}
