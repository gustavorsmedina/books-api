package tech.gustavomedina.booksapi.mapper;

import org.springframework.stereotype.Component;
import tech.gustavomedina.booksapi.model.Author;
import tech.gustavomedina.booksapi.model.Book;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.request.BookRequestDto;
import tech.gustavomedina.booksapi.payload.response.BookResponseDto;

@Component
public class BookMapper  {

    public Book convertBookRequestDtoToBook(BookRequestDto bookRequestDto, Author author){

        return new Book(
                null,
                bookRequestDto.name(),
                bookRequestDto.pages(),
                bookRequestDto.genre(),
                bookRequestDto.synopsis(),
                bookRequestDto.publicationDate(),
                bookRequestDto.isbn(),
                author
        );
    }

    public BookResponseDto convertBookToBookResponseDto(Book book){

        return new BookResponseDto(
                book.getId(),
                book.getName(),
                book.getPages(),
                book.getGenre(),
                book.getSynopsis(),
                book.getPublicationDate(),
                book.getIsbn(),
                new AuthorResponseDto(
                        book.getAuthor().getId(),
                        book.getAuthor().getName(),
                        book.getAuthor().getBirthDate(),
                        book.getAuthor().getBiography())
        );
    }

}
