package com.banco.taller2.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarArgumentoInvalido(IllegalArgumentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> manejarEstadoIlegal(IllegalStateException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErroresInesperados(IllegalStateException exception){
        return new ResponseEntity<>("Esta excepción fue capturada de forma genérica", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarErrores(RuntimeException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException exception){
        Map<String,String> errores = new HashMap<>();
        exception
                .getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) ->{
                            String parametro = ((FieldError) error).getField();
                            String mensaje = error.getDefaultMessage();
                            errores.put(parametro, mensaje);

                        });
                        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
