package tech.gustavomedina.booksapi.exception;

public class InvalidGenreException extends RuntimeException{

    public InvalidGenreException(String message) {
        super(message);
    }

}
