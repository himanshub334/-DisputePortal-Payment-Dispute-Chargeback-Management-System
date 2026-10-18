package com.example.dispute.api;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
 public Map<String,Object> validation(MethodArgumentNotValidException ex){return Map.of("error","Validation failed","details",ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).toList());}
 @ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
 public Map<String,String> state(IllegalStateException ex){return Map.of("error",ex.getMessage());}
}
