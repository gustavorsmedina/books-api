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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.gustavomedina.booksapi.payload.request.AuthorRequestDto;
import tech.gustavomedina.booksapi.payload.request.AuthorUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.response.AuthorWithBookListResponseDto;

@Tag(name = "Authors", description = "Author management.")
public interface AuthorController {

    @Operation(
            summary = "Create a new author.",
            method = "POST",
            description = "Service to create a new author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new author. Returns author URI in headers."),
            @ApiResponse(responseCode = "422", description = "Biography must be unique.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.", content = @Content)
    })
    ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto);

    @Operation(
            summary = "Get all authors.",
            method = "POST",
            description = "Service to get all authors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The list was returned successfully.")
    })
    @PageableAsQueryParam
    ResponseEntity<Page<AuthorWithBookListResponseDto>> getAllAuthors(@Parameter(hidden = true) @PageableDefault(sort = {"name"}) Pageable pageable);

    @Operation(
            summary = "Get author by id.",
            method = "GET",
            description = "Service to get an author by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found successfully."),
            @ApiResponse(responseCode = "404", description = "Author not found.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.", content = @Content),
    })
    ResponseEntity<AuthorWithBookListResponseDto> getAuthorById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Update author by id.",
            method = "PUT",
            description = "Service to update an author by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The author was successfully updated."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
            @ApiResponse(responseCode = "422", description = "Biography must be unique."),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.")
    })
    ResponseEntity<AuthorResponseDto> updateAuthorById(@PathVariable("id") Integer id, @RequestBody @Valid AuthorUpdateRequestDto authorUpdateRequestDto);

    @Operation(
            summary = "Delete author by id.",
            method = "DELETE",
            description = "Service to delete an author by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The author was successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters.")
    })
    ResponseEntity<Void> deleteAuthorById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Get authors by name.",
            method = "GET",
            description = "Service to found authors by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The author or authors have been successfully retrieved."),
    })
    @PageableAsQueryParam
    ResponseEntity<Page<AuthorWithBookListResponseDto>> getAuthorByName(@Parameter(hidden = true) @PageableDefault(sort = {"name"}) Pageable pageable, @RequestParam("name") String name);

}