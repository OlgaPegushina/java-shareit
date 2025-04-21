package ru.practicum.shareit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse repositoryNotFoundExceptionHandle(Exception e) {
        return new ExceptionResponse("Запрашиваемый ресурс не найден", e.getMessage());
    }

    @ExceptionHandler(DuplicatedDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse repositoryDuplicatedDataExceptionHandle(Exception e) {
        return new ExceptionResponse("Ресурс дублируется", e.getMessage());
    }

    @Getter
    @AllArgsConstructor
    static class ExceptionResponse {
        String error;
        String description;
    }
}
