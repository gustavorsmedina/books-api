package tech.gustavomedina.booksapi.infra.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tech.gustavomedina.booksapi.exception.InvalidGenreException;
import tech.gustavomedina.booksapi.exception.NotFoundEntityException;
import tech.gustavomedina.booksapi.exception.UnprocessableEntityException;
import tech.gustavomedina.booksapi.payload.response.ExceptionResponseDto;
import tech.gustavomedina.booksapi.payload.response.ExceptionResponseWithFieldsDto;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ExceptionResponseDto> handleNotFoundEntityException(NotFoundEntityException ex, HttpServletRequest request){

        var status = HttpStatus.NOT_FOUND;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseWithFieldsDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((e) -> errors.put(e.getField(), e.getDefaultMessage()));

        var message = "All arguments must be valid.";
        var status = HttpStatus.BAD_REQUEST;
        var response = new ExceptionResponseWithFieldsDto(LocalDateTime.now(), status.getReasonPhrase(), message, request.getRequestURI(), errors);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnprocessableEntityException(UnprocessableEntityException ex, HttpServletRequest request){

        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(InvalidGenreException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidGenreException(InvalidGenreException ex, HttpServletRequest request){

        var status = HttpStatus.BAD_REQUEST;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request){

        var message = "One of the provided data is invalid.";
        var status = HttpStatus.BAD_REQUEST;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), message, request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request){

        var status = HttpStatus.NOT_FOUND;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDto> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){

        var status = HttpStatus.BAD_REQUEST;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ExceptionResponseDto> handleInternalServerError(HttpServerErrorException ex, HttpServletRequest request){

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = new ExceptionResponseDto(LocalDateTime.now(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }


}
