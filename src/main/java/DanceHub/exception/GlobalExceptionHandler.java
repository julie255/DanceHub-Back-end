package DanceHub.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiError handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ConflictException.class)
    public ApiError handleConflict(ConflictException ex, HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    public ApiError handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    public ApiError handleForbidden(RuntimeException ex, HttpServletRequest request) {
        return buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        String message = fieldError == null
                ? "Requete invalide"
                : fieldError.getField() + " " + fieldError.getDefaultMessage();
        return buildError(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiError handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiError handleUnreadableBody(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Corps de requete invalide", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ApiError handleUnhandled(Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue", request.getRequestURI());
    }

    private ApiError buildError(HttpStatus status, String message, String path) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, path);
    }
}
