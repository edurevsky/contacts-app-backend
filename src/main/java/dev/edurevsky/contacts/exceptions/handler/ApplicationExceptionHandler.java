package dev.edurevsky.contacts.exceptions.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private final ObjectMapper objectMapper;

    public ApplicationExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorView handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        Integer status = NOT_FOUND.value();
        String error = NOT_FOUND.name();
        String message = e.getMessage();
        String path = request.getServletPath();
        return new ErrorView(status, error, message, path);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public void handleBadRequest(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer status = BAD_REQUEST.value();
        String error = BAD_REQUEST.name();
        Map<String, String> messages = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fe -> messages.put(fe.getField(), fe.getDefaultMessage()));
        String path = request.getServletPath();
        Map<String, Object> obj = new HashMap<>();
        obj.put("status", status);
        obj.put("error", error);
        obj.put("messages", messages);
        obj.put("path", path);
        objectMapper.writeValue(response.getOutputStream(), obj);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorView handleEmptyResultDataAccess(EmptyResultDataAccessException e, HttpServletRequest request) {
        Integer status = NOT_FOUND.value();
        String error = NOT_FOUND.name();
        String message = e.getMessage();
        String path = request.getServletPath();
        return new ErrorView(status, error, message, path);
    }
}
