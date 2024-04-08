CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) UNIQUE NOT NULL,
    genre ENUM('FICTION', 'MYSTERY', 'HORROR', 'ROMANCE', 'SCIENCE_FICTION', 'FANTASY', 'DRAMA', 'COMEDY', 'ADVENTURE', 'CRIME', 'PHILOSOPHY', 'THRILLER', 'SUSPENSE', 'ACTION', 'HISTORICAL', 'CLASSIC_LITERATURE', 'YOUNG_ADULT', 'CHILDREN', 'MAGIC_REALISM', 'DYSTOPIA', 'BIOGRAPHY', 'AUTOBIOGRAPHY', 'ESSAY', 'POETRY', 'DYSTOPIAN_FICTION', 'MYTHOLOGY', 'SPIRITUALITY', 'SELF_HELP', 'HISTORICAL_ROMANCE', 'FAIRY_TALE', 'GRAPHIC_NOVEL') NOT NULL,    synopsis VARCHAR(255) UNIQUE NOT NULL,
    publication_date DATE NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_books_authors
        FOREIGN KEY (author_id)
        REFERENCES authors(id)
        ON DELETE CASCADE
);