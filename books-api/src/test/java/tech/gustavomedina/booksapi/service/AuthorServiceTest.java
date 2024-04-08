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
import tech.gustavomedina.booksapi.mapper.AuthorMapper;
import tech.gustavomedina.booksapi.model.Author;
import tech.gustavomedina.booksapi.payload.request.AuthorRequestDto;
import tech.gustavomedina.booksapi.payload.request.AuthorUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.response.AuthorWithBookListResponseDto;
import tech.gustavomedina.booksapi.repository.AuthorRepository;
import tech.gustavomedina.booksapi.service.impl.AuthorServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Captor
    private ArgumentCaptor<Author> authorArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    private Author author;
    private AuthorRequestDto authorRequestDto;
    private AuthorResponseDto authorResponseDto;
    private AuthorWithBookListResponseDto authorWithBookListResponseDto;
    private AuthorUpdateRequestDto authorUpdateRequestDto;

    @BeforeEach
    void setup(){
        author = new Author(1, "some name", LocalDate.of(2000, 2, 2), "some biography", Collections.emptyList());
        authorRequestDto = new AuthorRequestDto("some name", LocalDate.of(2000, 2, 2), "some biography");
        authorResponseDto = new AuthorResponseDto(1, "some name", LocalDate.of(2000, 2, 2), "some biography");
        authorWithBookListResponseDto = new AuthorWithBookListResponseDto(1, "some name", LocalDate.of(2000, 2, 2), "some biography", Collections.emptyList());
        authorUpdateRequestDto = new AuthorUpdateRequestDto("new name", LocalDate.of(2002, 2, 2), "new biography");
    }

    @Nested
    class createAuthor{

        @Test
        @DisplayName("Should successfully create an author.")
        void shouldSuccessfullyCreateAnAuthor(){
            // Arrange
            when(authorMapper.convertAuthorRequestDtoToAuthor(authorRequestDto)).thenReturn(author);
            when(authorRepository.save(authorArgumentCaptor.capture())).thenReturn(author);
            when(authorMapper.convertAuthorToAuthorResponseDto(author)).thenReturn(authorResponseDto);

            // Act
            var output = authorService.createAuthor(authorRequestDto);

            // Assert
            assertNotNull(output);
            assertEquals(output.name(), authorArgumentCaptor.getValue().getName());
            assertEquals(output.birthDate(), authorArgumentCaptor.getValue().getBirthDate());
            assertEquals(output.biography(), authorArgumentCaptor.getValue().getBiography());
            verify(authorRepository, times(1)).save(any(Author.class));
            verify(authorMapper, times(1)).convertAuthorToAuthorResponseDto(any(Author.class));
        }

        @Test
        @DisplayName("Should throw an exception for duplicate author biographies.")
        void shouldThrowAnExceptionForDuplicateAuthorBiographies(){
            // Arrange
            when(authorRepository.existsByBiography(authorRequestDto.biography())).thenReturn(true);

            // Act & Assert
            assertThrows(UnprocessableEntityException.class, () -> authorService.createAuthor(authorRequestDto));
        }

    }

    @Nested
    class getAllAuthors{

        @Test
        @DisplayName("Should successfully return all authors.")
        void shouldSuccessfullyReturnAllAuthors(){
            // Arrange
            List<Author> authorsList = new ArrayList<>();
            authorsList.add(author);

            var pageable = Pageable.unpaged();
            Page<Author> authorsPage = new PageImpl<>(authorsList);

            when(authorRepository.findAll(pageable)).thenReturn(authorsPage);
            when(authorMapper.convertAuthorToAuthorWithBookListResponseDto(authorsPage.getContent().get(0))).thenReturn(authorWithBookListResponseDto);

            // Act
            var output = authorService.getAllAuthors(pageable);

            // Assert
            assertNotNull(output);
            assertEquals(authorsPage.getTotalElements(), output.getTotalElements());
            verify(authorMapper, times(1)).convertAuthorToAuthorWithBookListResponseDto(any(Author.class));
        }

    }

    @Nested
    class getAuthorById{

        @Test
        @DisplayName("Should successfully return an author if the ID exists.")
        void shouldSuccessfullyReturnAnAuthorIfTheIdExists(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(author));
            when(authorMapper.convertAuthorToAuthorWithBookListResponseDto(author)).thenReturn(authorWithBookListResponseDto);

            // Act
            var output = authorService.getAuthorById(author.getId());

            // Assert
            assertNotNull(output);
            assertEquals(output.id(), integerArgumentCaptor.getValue());
            verify(authorRepository, times(1)).findById(anyInt());
            verify(authorMapper, times(1)).convertAuthorToAuthorWithBookListResponseDto(any(Author.class));
        }

        @Test
        @DisplayName("Should throw exception for searching non existent author ID.")
        void shouldThrowExceptionForSearchingNonExistentAuthorId(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundEntityException.class, () -> {
                authorService.getAuthorById(author.getId());
            });
        }
    }

    @Nested
    class updateAuthorById{

        @Test
        @DisplayName("Should successfully update an author.")
        void shouldSuccessfullyUpdateAnAuthor(){
            // Arrange
            var authorUpdatedResponseDto = new AuthorResponseDto(author.getId(), authorUpdateRequestDto.name(), authorUpdateRequestDto.birthDate(), authorUpdateRequestDto.biography());

            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(author));
            when(authorRepository.save(authorArgumentCaptor.capture())).thenReturn(author);
            when(authorMapper.convertAuthorToAuthorResponseDto(authorArgumentCaptor.capture())).thenReturn(authorUpdatedResponseDto);

            // Act
            var output = authorService.updateAuthorById(author.getId(), authorUpdateRequestDto);

            // Assert
            assertNotNull(output);
            assertEquals(author.getId(), authorArgumentCaptor.getValue().getId());
            assertEquals(authorUpdateRequestDto.name(), authorArgumentCaptor.getValue().getName());
            assertEquals(authorUpdateRequestDto.birthDate(), authorArgumentCaptor.getValue().getBirthDate());
            assertEquals(authorUpdateRequestDto.biography(), authorArgumentCaptor.getValue().getBiography());
            assertEquals(authorUpdateRequestDto.name(), output.name());
            verify(authorRepository, times(1)).findById(anyInt());
            verify(authorRepository, times(1)).existsByBiography(anyString());
            verify(authorRepository, times(1)).save(any(Author.class));
            verify(authorMapper, times(1)).convertAuthorToAuthorResponseDto(any(Author.class));
        }

        @Test
        @DisplayName("Should throw an exception for updating a non existent author ID.")
        void shouldThrowAnExceptionForUpdatingANonExistentAuthorId(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundEntityException.class, () -> {
                authorService.updateAuthorById(author.getId(), authorUpdateRequestDto);
            });
        }

        @Test
        @DisplayName("Should throw exception for updating author with existing biography.")
        void shouldThrowExceptionForUpdatingAuthorWithExistingBiography(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(author));
            when(authorRepository.existsByBiography(authorUpdateRequestDto.biography())).thenReturn(true);

            // Act & Assert
            assertThrows(UnprocessableEntityException.class, () -> authorService.updateAuthorById(author.getId(), authorUpdateRequestDto));
        }

    }

    @Nested
    class deleteAuthorById{

        @Test
        @DisplayName("Should successfully delete an author.")
        void shouldSuccessfullyDeleteAnAuthor(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.of(author));
            doNothing().when(authorRepository).delete(authorArgumentCaptor.capture());

            // Act
            authorService.deleteAuthorById(author.getId());

            // Assert
            assertEquals(author.getId(), integerArgumentCaptor.getValue());
            verify(authorRepository, times(1)).findById(anyInt());
            verify(authorRepository, times(1)).delete(any(Author.class));
        }

        @Test
        @DisplayName("Should throw exception for deleting non existent author ID.")
        void shouldThrowExceptionForDeletingNonExistentAuthorId(){
            // Arrange
            when(authorRepository.findById(integerArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(NotFoundEntityException.class, () -> {
                authorService.deleteAuthorById(author.getId());
            });
        }

    }

    @Nested
    class getAuthorByName{

        @Test
        @DisplayName("Should return a list of authors with the searched name.")
        void shouldReturnAListOfAuthorsWithTheSearchedName(){
            // Arrange
            List<Author> authorsList = new ArrayList<>();
            authorsList.add(author);

            var pageable = Pageable.unpaged();
            Page<Author> authorsPage = new PageImpl<>(authorsList);

            when(authorRepository.findByNameContaining(pageable, author.getName())).thenReturn(authorsPage);
            when(authorMapper.convertAuthorToAuthorWithBookListResponseDto(authorsPage.getContent().get(0))).thenReturn(authorWithBookListResponseDto);

            // Act
            var output = authorService.getAuthorByName(pageable, author.getName());

            // Assert
            assertNotNull(output);
            assertEquals(author.getName(), output.getContent().get(0).name());
        }

    }
}