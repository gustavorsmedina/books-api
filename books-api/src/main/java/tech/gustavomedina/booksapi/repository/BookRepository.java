package tech.gustavomedina.booksapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.gustavomedina.booksapi.model.Book;
import tech.gustavomedina.booksapi.model.BookGenre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findByNameContaining(Pageable pageable, String name);
    boolean existsByName(String name);
    boolean existsByIsbn(String isbn);
    boolean existsBySynopsis(String synopsis);
    Page<Book> findAllByGenre(Pageable pageable, BookGenre genre);

}
