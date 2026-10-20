package br.com.edge.UniNotas.handler;

import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e)
    {
        ErrorResponse response = ErrorResponse.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND.value()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> BadRequestException(BadRequestException e)
    {
        ErrorResponse response = ErrorResponse.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST.value()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
