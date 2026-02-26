package med.salus.api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import med.salus.api.dto.error.ApiError;
import med.salus.api.dto.error.ApiErrorResponse;
import med.salus.api.dto.error.ApiErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.BAD_REQUEST;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflictException(ConflictException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.CONFLICT;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.NOT_FOUND;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiError> handleUnprocessableEntityException(
            UnprocessableEntityException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.UNPROCESSABLE_CONTENT;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.unprocessableContent().body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(
            EntityNotFoundException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.NOT_FOUND;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ApiErrorValidation> fields = ex.getFieldErrors().stream()
                .map(error -> new ApiErrorValidation(error.getField(), error.getDefaultMessage()))
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation error", fields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.BAD_REQUEST;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                "Invalid request body",
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {
        HttpStatus errorType = HttpStatus.UNAUTHORIZED;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                "Credentials are invalid",
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {
        HttpStatus errorType = HttpStatus.UNAUTHORIZED;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                "Failed to authenticate",
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        HttpStatus errorType = HttpStatus.FORBIDDEN;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                "Access denied",
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        HttpStatus errorType = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                "Internal Server Error",
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }
}
