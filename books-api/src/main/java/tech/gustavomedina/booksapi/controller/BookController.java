package tech.gustavomedina.booksapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.gustavomedina.booksapi.payload.request.BookRequestDto;
import tech.gustavomedina.booksapi.payload.request.BookUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.BookResponseDto;

@Tag(name = "Books", description = "Book management.")
public interface BookController {

    @Operation(
            summary = "Create a new book.",
            method = "POST",
            description = "Service to create a new book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new book. Returns book URI in headers."),
            @ApiResponse(responseCode = "422", description = "Name, ISBN and synopsis must be unique.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto);

    @Operation(
            summary = "Get all books.",
            method = "GET",
            description = "Service to get all books."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The list was returned successfully.")
    })
    @PageableAsQueryParam
    ResponseEntity<Page<BookResponseDto>> getAllBooks(@Parameter(hidden = true) @PageableDefault(sort = {"name"}) Pageable pageable);

    @Operation(
            summary = "Get book by id.",
            method = "GET",
            description = "Service to get an book by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found successfully."),
            @ApiResponse(responseCode = "404", description = "Book not found.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.", content = @Content)
    })
    ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Update book by id.",
            method = "PUT",
            description = "Service to update an book by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was successfully updated."),
            @ApiResponse(responseCode = "404", description = "Book not found."),
            @ApiResponse(responseCode = "422", description = "Name, ISBN and synopsis must be unique."),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.")
    })
    ResponseEntity<BookResponseDto> updateAuthorById(@PathVariable("id") Integer id, @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto);

    @Operation(
            summary = "Delete book by id.",
            method = "DELETE",
            description = "Service to delete an book by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Book not found."),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.")
    })
    ResponseEntity<Void> deleteBookById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Get books by name.",
            method = "GET",
            description = "Service to found books by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found successfully or book list is empty."),
    })
    @PageableAsQueryParam
    ResponseEntity<Page<BookResponseDto>> getBooksByName(@Parameter(hidden = true) @PageableDefault(sort = {"name"}) Pageable pageable, @RequestParam("name") String name);

    @Operation(
            summary = "Filter books by genre.",
            method = "GET",
            description = "Service to filter books by genre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found successfully."),
            @ApiResponse(responseCode = "400", description = "The genre doesn't exist.", content = @Content)
    })
    @PageableAsQueryParam
    ResponseEntity<Page<BookResponseDto>> getBooksByGenre(@Parameter(hidden = true) @PageableDefault(sort = {"name"}) Pageable pageable, @PathVariable("genre") String genre);

}



