package mx.nach.pruebaTecnica.errors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.log4j.Log4j2;
import mx.nach.pruebaTecnica.dto.Response;

/*
 * Clase para manejar los errores de la aplicación.
 */
@ControllerAdvice
@Log4j2
public class ErrorControl {

    /*
     * Control de errores para manejar excepciones de tipo IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIlegalArgumentException(IllegalArgumentException ex, WebRequest request) {
       
        log.error("Error: {}", ex.getMessage(), ex);

        Response response = new Response(
            ex,
            ex.getMessage(),
            Boolean.FALSE
        );
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /*
     * Control de errores para manejar excepciones de validación de las anotaciones.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> manejarValidacion(MethodArgumentNotValidException ex) {

        log.error("Error: {}", ex.getMessage(), ex);

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String mensaje = String.join(", ", errores);
        Response response = new Response(
            ex.getMessage(),
            mensaje,
            Boolean.FALSE
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
