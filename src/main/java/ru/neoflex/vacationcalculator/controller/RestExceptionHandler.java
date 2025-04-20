package ru.neoflex.vacationcalculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик исключений для REST-контроллеров.
 * Позволяет обрабатывать исключения и формировать ответы клиенту.
 */
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * Обработчик исключения MethodArgumentNotValidException.
     * Используется для формирования осмысленного ответа клиенту в случае неправильных данных запроса.
     * @param e исключение, связанное с неверными аргументами метода
     * @return HTTP-статус BAD_REQUEST и сообщение об ошибке с описанием проблемы
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        String field = fieldError.getField();
        String errorMessage = fieldError.getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Ошибочное значение для " + field + ": " + errorMessage);
    }
}
