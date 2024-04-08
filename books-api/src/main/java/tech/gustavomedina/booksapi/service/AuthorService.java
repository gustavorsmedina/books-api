package tech.gustavomedina.booksapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.gustavomedina.booksapi.payload.request.AuthorRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.request.AuthorUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorWithBookListResponseDto;

import java.util.List;

public interface AuthorService {

    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
    Page<AuthorWithBookListResponseDto> getAllAuthors(Pageable pageable);
    AuthorWithBookListResponseDto getAuthorById(Integer id);
    AuthorResponseDto updateAuthorById(Integer id, AuthorUpdateRequestDto authorUpdateRequestDto);
    void deleteAuthorById(Integer id);
    Page<AuthorWithBookListResponseDto> getAuthorByName(Pageable pageable, String name);

}
