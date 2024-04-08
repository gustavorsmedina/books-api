package tech.gustavomedina.booksapi.mapper;

import org.springframework.stereotype.Component;
import tech.gustavomedina.booksapi.model.Author;
import tech.gustavomedina.booksapi.payload.request.AuthorRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.response.AuthorWithBookListResponseDto;
import tech.gustavomedina.booksapi.payload.response.BookWithoutAuthorResponseDto;

import java.util.Collections;

@Component
public class AuthorMapper {

    public Author convertAuthorRequestDtoToAuthor(AuthorRequestDto authorRequestDto){

        return new Author(
                null,
                authorRequestDto.name(),
                authorRequestDto.birthDate(),
                authorRequestDto.biography(),
                Collections.emptyList()
        );
    }

    public AuthorResponseDto convertAuthorToAuthorResponseDto(Author author){

        return new AuthorResponseDto(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getBiography()
        );
    }

    public AuthorWithBookListResponseDto convertAuthorToAuthorWithBookListResponseDto(Author author){

        return new AuthorWithBookListResponseDto(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getBiography(),
                author.getBooks()
                        .stream()
                        .map((b) -> new BookWithoutAuthorResponseDto(
                                b.getId(),
                                b.getName(),
                                b.getPages(),
                                b.getGenre(),
                                b.getSynopsis(),
                                b.getPublicationDate(),
                                b.getIsbn())).toList()
        );
    }

}
