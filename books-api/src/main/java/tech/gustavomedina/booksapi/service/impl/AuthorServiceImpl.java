package tech.gustavomedina.booksapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.gustavomedina.booksapi.exception.NotFoundEntityException;
import tech.gustavomedina.booksapi.exception.UnprocessableEntityException;
import tech.gustavomedina.booksapi.mapper.AuthorMapper;
import tech.gustavomedina.booksapi.payload.request.AuthorRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorResponseDto;
import tech.gustavomedina.booksapi.payload.request.AuthorUpdateRequestDto;
import tech.gustavomedina.booksapi.payload.response.AuthorWithBookListResponseDto;
import tech.gustavomedina.booksapi.repository.AuthorRepository;
import tech.gustavomedina.booksapi.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {

        var author = authorMapper.convertAuthorRequestDtoToAuthor(authorRequestDto);

        if(authorRepository.existsByBiography(authorRequestDto.biography())) throw new UnprocessableEntityException("An author has already been registered with this biography.");

        var authorSaved = authorRepository.save(author);

        return authorMapper.convertAuthorToAuthorResponseDto(authorSaved);
    }

    @Override
    public Page<AuthorWithBookListResponseDto> getAllAuthors(Pageable pageable) {

        var authors = authorRepository.findAll(pageable);

        return authors.map(authorMapper::convertAuthorToAuthorWithBookListResponseDto);
    }

    @Override
    public AuthorWithBookListResponseDto getAuthorById(Integer id) {

        var author = authorRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("The author doesn't exist."));

        return authorMapper.convertAuthorToAuthorWithBookListResponseDto(author);
    }

    @Override
    public AuthorResponseDto updateAuthorById(Integer id, AuthorUpdateRequestDto authorUpdateRequestDto) {

        var author = authorRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("The author doesn't exist."));

        if(!author.getBiography().equals(authorUpdateRequestDto.biography()) && authorRepository.existsByBiography(authorUpdateRequestDto.biography())) throw new UnprocessableEntityException("An author has already been registered with this biography.");

        if(authorUpdateRequestDto.name() != null) author.setName(authorUpdateRequestDto.name());
        if(authorUpdateRequestDto.birthDate() != null) author.setBirthDate(authorUpdateRequestDto.birthDate());
        if(authorUpdateRequestDto.biography() != null) author.setBiography(authorUpdateRequestDto.biography());

        var authorSaved = authorRepository.save(author);

        return authorMapper.convertAuthorToAuthorResponseDto(authorSaved);
    }

    @Override
    public void deleteAuthorById(Integer id) {

        var author = authorRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("The author doesn't exist."));

        authorRepository.delete(author);
    }

    @Override
    public Page<AuthorWithBookListResponseDto> getAuthorByName(Pageable pageable, String name) {

        var authors = authorRepository.findByNameContaining(pageable, name);

        return authors.map(authorMapper::convertAuthorToAuthorWithBookListResponseDto);
    }


}
