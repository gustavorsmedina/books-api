package tech.gustavomedina.booksapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "pages")
    private Short pages;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Column(name = "synopsis", unique = true)
    private String synopsis;

    @Column(name = "publication_date", unique = true)
    private LocalDate publicationDate;

    @Column(name = "isbn")
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

}
