package com.example.apsoft.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

// Перехватчик ошибок на различные программные сбои, такие как:
// 1) Пользователь пытается получить из БД запись с несуществующим id
// 2) Пользователь не положил файл в тело запроса

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElementException(NoSuchElementException ex){
        return exceptionHandlerPattern(ex, Code.ID_NOT_EXISTS, "Записи с таким id не существует");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> fileNotFoundException(FileNotFoundException ex){
        return exceptionHandlerPattern(ex, Code.FILE_NOT_FOUND, "Вы не загрузили файл");
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> multipartException(MultipartException ex){
        return exceptionHandlerPattern(ex, Code.FILE_ERROR, "Ошибка файловой системы, вставьте файл заново");
    }

    private ResponseEntity<ErrorResponse> exceptionHandlerPattern(Exception ex, Code code, String message){
        log.error("Error: " + ex.getMessage());
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(code)
                .message(message)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
