package tech.gustavomedina.booksapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gustavomedina.booksapi.model.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Page<Author> findByNameContaining(Pageable pageable, String name);
    boolean existsByBiography(String biography);

}
