package tech.gustavomedina.booksapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tech.gustavomedina.booksapi.exception.NotFoundEntityException;
import tech.gustavomedina.booksapi.exception.UnprocessableEntityException;
import tech.gustavomedina.booksapi.mapper.BookMapper;
import tech.gustavomedina.booksapi.model.Author;
import tech.gustavomedina.booksapi.model.Book;
import tech.gustavomedina.booksapi.model.BookGenre;
import tech.gustavomedina.booksapi.payload.request.BookRequestDto;
import tech.gustavomedina.booksapi.payload.request.BookUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.response.BookResponseDto;
import tech.gustavomedina.booksapi.repository.AuthorRepository;
import tech.gustavomedina.booksapi.repository.BookRepository;
import tech.gustavomedina.booksapi.service.impl.BookServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    private Author author;
    private AuthorResponseDto authorResponseDto;
    private Book book;
    private BookRequestDto bookRequestDto;
    private BookResponseDto bookResponseDto;
    private BookUpdateRequestDto bookUpdateRequestDto;

    @BeforeEach
    void setup(){
        author = new Author(1, "some name", LocalDate.of(2000, 2, 2), "some biography", new ArrayList<>());
        authorResponseDto = new AuthorResponseDto(1, "some name", LocalDate.of(2000, 2, 2), "some biography");
        book = new Book(1, "some name", (short) 20, BookGenre.ADVENTURE, "some synopsis", LocalDate.of(2024, 4, 4), "123-456", author);
        bookRequestDto = new BookRequestDto("some name", (short) 20, BookGenre.ADVENTURE, "some synopsis", LocalDate.of(2024, 4, 4), "123-456", 1);
        bookResponseDto = new BookResponseDto(1 , "some name", (short) 20, BookGenre.ADVENTURE, "some synopsis", LocalDate.of(2024, 4, 4), "123-456", authorResponseDto);
        bookUpdateRequestDto = new BookUpdateRequestDto("new name", (short) 20, BookGenre.ADVENTURE, "new synopsis", LocalDate.of(2024, 6, 6), "123-456", 1);
    }

    @Nested
    class createBook{

        @Test
        @DisplayName("Should successfully create a book.")
        void shouldSuccessfullyCreateABook(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(author));
            when(bookMapper.convertBookRequestDtoToBook(bookRequestDto, author)).thenReturn(book);
            when(bookRepository.save(bookArgumentCaptor.capture())).thenReturn(book);
            when(bookMapper.convertBookToBookResponseDto(book)).thenReturn(bookResponseDto);

            // Act
            var output = bookService.createBook(bookRequestDto);

            // Assert
            assertNotNull(output);
            assertEquals(output.name(), bookArgumentCaptor.getValue().getName());
            assertEquals(output.genre(), bookArgumentCaptor.getValue().getGenre());
            assertEquals(output.pages(), bookArgumentCaptor.getValue().getPages());
            assertEquals(output.synopsis(), bookArgumentCaptor.getValue().getSynopsis());
            assertEquals(output.publicationDate(), bookArgumentCaptor.getValue().getPublicationDate());
            assertEquals(output.isbn(), bookArgumentCaptor.getValue().getIsbn());
            assertEquals(output.author().name(), bookArgumentCaptor.getValue().getAuthor().getName());
            assertEquals(output.author().birthDate(), bookArgumentCaptor.getValue().getAuthor().getBirthDate());
            assertEquals(output.author().biography(), bookArgumentCaptor.getValue().getAuthor().getBiography());
            assertFalse(author.getBooks().isEmpty());
            verify(bookRepository, times(1)).save(any(Book.class));
            verify(bookMapper, times(1)).convertBookToBookResponseDto(any(Book.class));
        }

        @Test
        @DisplayName("Should return exception if the book data already exists.")
        void shouldReturnExceptionIfTheBookDataAlreadyExists(){
            // Arrange
            when(bookRepository.existsByName(bookRequestDto.name())).thenReturn(true);

            // Act & Assert
            assertThrows(UnprocessableEntityException.class, () -> bookService.createBook(bookRequestDto));
            verify(bookRepository, times(1)).existsByName(anyString());
        }

    }

    @Nested
    class getAllBooks{

        @Test
        @DisplayName("Should successfully return all books.")
        void shouldSuccessfullyReturnAllBooks(){
            // Arrange
            List<Book> booksList = new ArrayList<>();
            booksList.add(book);

            var pageable = Pageable.unpaged();
            Page<Book> booksPage = new PageImpl<>(booksList);

            when(bookRepository.findAll(pageable)).thenReturn(booksPage);
            when(bookMapper.convertBookToBookResponseDto(booksPage.getContent().get(0))).thenReturn(bookResponseDto);

            // Act
            var output = bookService.getAllBooks(pageable);

            // Assert
            assertNotNull(output);
            assertEquals(booksPage.getTotalElements(), output.getTotalElements());
            verify(bookMapper, times(1)).convertBookToBookResponseDto(any(Book.class));
        }

    }

    @Nested
    class getBookById{

        @Test
        @DisplayName("Should successfully return a book if the ID exists.")
        void shouldSuccessfullyReturnABOokIfTheIdExists(){
            // Arrange
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(book));
            when(bookMapper.convertBookToBookResponseDto(book)).thenReturn(bookResponseDto);

            // Act
            var output = bookService.getBookById(author.getId());

            // Assert
            assertNotNull(output);
            assertEquals(output.id(), integerArgumentCaptor.getValue());
            verify(bookRepository, times(1)).findById(anyInt());
            verify(bookMapper, times(1)).convertBookToBookResponseDto(any(Book.class));
        }

        @Test
        @DisplayName("Should throw exception for searching non existent book ID.")
        void shouldThrowExceptionForSearchingNonExistentBookId(){
            // Arrange
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundEntityException.class, () -> {
                bookService.getBookById(book.getId());
            });
        }
    }

    @Nested
    class updateAuthorById{

        @Test
        @DisplayName("Should successfully update a book.")
        void shouldSuccessfullyUpdateABook(){
            // Arrange
            var bookUpdatedResponseDto = new BookResponseDto(book.getId(), bookUpdateRequestDto.name(), bookUpdateRequestDto.pages(), bookUpdateRequestDto.genre(), bookUpdateRequestDto.synopsis(), bookUpdateRequestDto.publicationDate(), bookUpdateRequestDto.isbn(), authorResponseDto);

            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(author));
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(book));
            when(bookRepository.save(bookArgumentCaptor.capture())).thenReturn(book);
            when(bookMapper.convertBookToBookResponseDto(bookArgumentCaptor.capture())).thenReturn(bookUpdatedResponseDto);

            // Act
            var output = bookService.updateBookById(book.getId(), bookUpdateRequestDto);

            // Assert
            assertNotNull(output);
            assertEquals(author.getId(), bookArgumentCaptor.getValue().getId());
            assertEquals(bookUpdateRequestDto.name(), bookArgumentCaptor.getValue().getName());
            assertEquals(bookUpdateRequestDto.pages(), bookArgumentCaptor.getValue().getPages());
            assertEquals(bookUpdateRequestDto.genre(), bookArgumentCaptor.getValue().getGenre());
            assertEquals(bookUpdateRequestDto.synopsis(), bookArgumentCaptor.getValue().getSynopsis());
            assertEquals(bookUpdateRequestDto.publicationDate(), bookArgumentCaptor.getValue().getPublicationDate());
            assertEquals(bookUpdateRequestDto.isbn(), bookArgumentCaptor.getValue().getIsbn());
            assertEquals(bookUpdateRequestDto.authorId(), bookArgumentCaptor.getValue().getAuthor().getId());
            assertEquals(bookUpdateRequestDto.name(), output.name());
            verify(bookRepository, times(1)).findById(anyInt());
            verify(bookRepository, times(1)).existsByName(anyString());
            verify(bookRepository, times(1)).save(any(Book.class));
            verify(bookMapper, times(1)).convertBookToBookResponseDto(any(Book.class));
        }

        @Test
        @DisplayName("Should throw an exception for updating a non existent book ID.")
        void shouldThrowAnExceptionForUpdatingANonExistentBookId(){
            // Arrange
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundEntityException.class, () -> {
                bookService.updateBookById(book.getId(), bookUpdateRequestDto);
            });
        }

        @Test
        @DisplayName("Should throw exception for updating book with existing data.")
        void shouldThrowExceptionForUpdatingBookWithExistingData(){
            // Arrange
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(book));
            when(bookRepository.existsByName(bookUpdateRequestDto.name())).thenReturn(true);

            // Act & Assert
            assertThrows(UnprocessableEntityException.class, () -> bookService.updateBookById(book.getId(), bookUpdateRequestDto));
        }

    }

    @Nested
    class deleteBookById{

        @Test
        @DisplayName("Should successfully delete a book.")
        void shouldSuccessfullyDeleteABook(){
            // Arrange
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(book));
            doNothing().when(bookRepository).delete(bookArgumentCaptor.capture());

            // Act
            bookService.deleteBookById(book.getId());

            // Assert
            assertEquals(book.getId(), integerArgumentCaptor.getValue());
            verify(bookRepository, times(1)).findById(anyInt());
            verify(bookRepository, times(1)).delete(any(Book.class));
        }

        @Test
        @DisplayName("Should throw exception for deleting non existent book ID.")
        void shouldThrowExceptionForDeletingNonExistentBookId(){
            // Arrange
            when(bookRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundEntityException.class, () -> {
                bookService.deleteBookById(book.getId());
            });
        }

    }

    @Nested
    class getBookByName{

        @Test
        @DisplayName("Should return a list of books with the searched name.")
        void shouldReturnAListOfBooksWithTheSearchedName(){
            // Arrange
            List<Book> booksList = new ArrayList<>();
            booksList.add(book);

            var pageable = Pageable.unpaged();
            Page<Book> booksPage = new PageImpl<>(booksList);

            when(bookRepository.findByNameContaining(pageable, book.getName())).thenReturn(booksPage);
            when(bookMapper.convertBookToBookResponseDto(booksPage.getContent().get(0))).thenReturn(bookResponseDto);

            // Act
            var output = bookService.getBooksByName(pageable, book.getName());

            // Assert
            assertNotNull(output);
            assertEquals(book.getName(), output.getContent().get(0).name());
        }

    }

    @Nested
    class getBookByGenre{

        @Test
        @DisplayName("Should return a list of books with the searched genre.")
        void shouldReturnAListOfBooksWithTheSearchedGenre(){
            // Arrange
            List<Book> booksList = new ArrayList<>();
            booksList.add(book);

            var pageable = Pageable.unpaged();
            Page<Book> booksPage = new PageImpl<>(booksList);

            when(bookRepository.findAllByGenre(pageable, book.getGenre())).thenReturn(booksPage);
            when(bookMapper.convertBookToBookResponseDto(booksPage.getContent().get(0))).thenReturn(bookResponseDto);

            // Act
            var output = bookService.getBooksByGenre(pageable, book.getGenre().name());

            // Assert
            assertNotNull(output);
            assertEquals(book.getGenre(), output.getContent().get(0).genre());
        }

    }
}