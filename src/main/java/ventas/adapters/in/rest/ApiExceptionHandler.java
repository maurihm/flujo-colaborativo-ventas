package ventas.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ventas.adapters.in.rest.dto.ProblemDetailDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetailDto> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ProblemDetailDto problem = new ProblemDetailDto(
                "https://api.ventas.tcsw/errores/validacion",
                "Entrada invalida",
                HttpStatus.BAD_REQUEST.value(),
                "Uno o mas campos no cumplen con el formato requerido",
                request.getRequestURI()
        );
        problem.setInvalidParams(errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ProblemDetailDto> handleNotFound(
            NoSuchElementException ex, HttpServletRequest request) {

        ProblemDetailDto problem = new ProblemDetailDto(
                "https://api.ventas.tcsw/errores/recurso-inexistente",
                "Recurso no encontrado",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetailDto> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {

        ProblemDetailDto problem = new ProblemDetailDto(
                "https://api.ventas.tcsw/errores/regla-negocio",
                "Conflicto o argumento invalido",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetailDto> handleGeneralError(
            Exception ex, HttpServletRequest request) {

        ProblemDetailDto problem = new ProblemDetailDto(
                "https://api.ventas.tcsw/errores/servidor",
                "Error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrio una condicion inesperada al procesar la solicitud",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}
