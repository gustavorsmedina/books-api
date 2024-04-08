package tech.gustavomedina.booksapi.controller.impl;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gustavomedina.booksapi.controller.AuthorController;
import tech.gustavomedina.booksapi.payload.request.AuthorRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.request.AuthorUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorWithBookListResponseDto;
import tech.gustavomedina.booksapi.service.impl.AuthorServiceImpl;

import java.net.URI;

@RestController
@RequestMapping("/v1/authors")
public class AuthorControllerImpl implements AuthorController {

    private final AuthorServiceImpl authorService;

    public AuthorControllerImpl(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto){

        var author = authorService.createAuthor(authorRequestDto);

        return ResponseEntity.created(URI.create("/v1/authors/" + author.id().toString())).body(author);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AuthorWithBookListResponseDto>> getAllAuthors(@PageableDefault(sort = {"name"}) Pageable pageable){

        var authors = authorService.getAllAuthors(pageable);

        return ResponseEntity.ok(authors);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorWithBookListResponseDto> getAuthorById(@PathVariable("id") Integer id){

        var author = authorService.getAuthorById(id);

        return ResponseEntity.ok(author);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseDto> updateAuthorById(@PathVariable("id") Integer id, @RequestBody @Valid AuthorUpdateRequestDto authorUpdateRequestDto){

        var author = authorService.updateAuthorById(id, authorUpdateRequestDto);

        return ResponseEntity.ok(author);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable("id") Integer id){

        authorService.deleteAuthorById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/search", params = "name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AuthorWithBookListResponseDto>> getAuthorByName(@PageableDefault(sort = {"name"}) Pageable pageable, @RequestParam("name") String name){

        var authors = authorService.getAuthorByName(pageable, name);

        return ResponseEntity.ok(authors);
    }

}
