package tech.gustavomedina.booksapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.gustavomedina.booksapi.exception.InvalidGenreException;
import tech.gustavomedina.booksapi.exception.NotFoundEntityException;
import tech.gustavomedina.booksapi.exception.UnprocessableEntityException;
import tech.gustavomedina.booksapi.mapper.BookMapper;
import tech.gustavomedina.booksapi.model.BookGenre;
import tech.gustavomedina.booksapi.payload.request.BookRequestDto;
import tech.gustavomedina.booksapi.payload.response.BookResponseDto;
import tech.gustavomedina.booksapi.payload.request.BookUpdateRequestDto;
import tech.gustavomedina.booksapi.repository.AuthorRepository;
import tech.gustavomedina.booksapi.repository.BookRepository;
import tech.gustavomedina.booksapi.service.BookService;
import tech.gustavomedina.booksapi.util.GenreUtil;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {

        var genreExists = GenreUtil.isValid(bookRequestDto.genre().toString());

        if(!genreExists) throw new InvalidGenreException("The genre doesn't exists.");
        if(bookRepository.existsByName(bookRequestDto.name())) throw new UnprocessableEntityException("A book has already been registered with this name.");
        if(bookRepository.existsByIsbn(bookRequestDto.isbn())) throw new UnprocessableEntityException("A book has already been registered with this ISBN.");
        if(bookRepository.existsBySynopsis(bookRequestDto.synopsis())) throw new UnprocessableEntityException("A book has already been registered with this synopsis.");

        var author = authorRepository
                .findById(bookRequestDto.authorId())
                .orElseThrow(() -> new NotFoundEntityException("The author doesn't exist."));

        var book = bookMapper.convertBookRequestDtoToBook(bookRequestDto, author);

        var bookSaved = bookRepository.save(book);

        author.getBooks().add(bookSaved);

        return bookMapper.convertBookToBookResponseDto(bookSaved);
    }

    @Override
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {

        var books = bookRepository.findAll(pageable);

        return books.map(bookMapper::convertBookToBookResponseDto);
    }

    @Override
    public BookResponseDto getBookById(Integer id) {

        var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("The book doesn't exist."));

        return bookMapper.convertBookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto updateBookById(Integer id, BookUpdateRequestDto bookUpdateRequestDto) {

        var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("The book doesn't exist."));

        var genreExists = GenreUtil.isValid(bookUpdateRequestDto.genre().toString());

        if(!genreExists) throw new InvalidGenreException("The genre doesn't exists.");
        if(!book.getName().equals(bookUpdateRequestDto.name()) && bookRepository.existsByName(bookUpdateRequestDto.name())) throw new UnprocessableEntityException("A book has already been registered with this name.");
        if(!book.getIsbn().equals(bookUpdateRequestDto.isbn()) && bookRepository.existsByIsbn(bookUpdateRequestDto.isbn())) throw new UnprocessableEntityException("A book has already been registered with this ISBN.");
        if(!book.getSynopsis().equals(bookUpdateRequestDto.synopsis()) && bookRepository.existsBySynopsis(bookUpdateRequestDto.synopsis())) throw new UnprocessableEntityException("A book has already been registered with this synopsis.");

        if(bookUpdateRequestDto.authorId() != null){
            var author = authorRepository.findById(bookUpdateRequestDto.authorId()).orElseThrow(() -> new NotFoundEntityException("The author doesn't exist."));
            book.setAuthor(author);
        }

        if(bookUpdateRequestDto.name() != null) book.setName(bookUpdateRequestDto.name());
        book.setGenre(bookUpdateRequestDto.genre());
        if(bookUpdateRequestDto.synopsis() != null) book.setSynopsis(bookUpdateRequestDto.synopsis());
        if(bookUpdateRequestDto.publicationDate() != null) book.setPublicationDate(bookUpdateRequestDto.publicationDate());
        if(bookUpdateRequestDto.isbn() != null) book.setIsbn(bookUpdateRequestDto.isbn());

        var bookSaved = bookRepository.save(book);

        return bookMapper.convertBookToBookResponseDto(bookSaved);
    }

    @Override
    public void deleteBookById(Integer id) {

        var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("The book doesn't exist."));

        bookRepository.delete(book);
    }

    @Override
    public Page<BookResponseDto> getBooksByName(Pageable pageable, String name) {
        var books = bookRepository.findByNameContaining(pageable, name);

        return books.map(bookMapper::convertBookToBookResponseDto);
    }

    @Override
    public Page<BookResponseDto> getBooksByGenre(Pageable pageable, String genre) {

        var genreExists = GenreUtil.isValid(genre);

        if(!genreExists) throw new InvalidGenreException("The genre doesn't exists.");

        var books = bookRepository.findAllByGenre(pageable, BookGenre.valueOf(genre.toUpperCase()));

        return books.map(bookMapper::convertBookToBookResponseDto);
    }

}
