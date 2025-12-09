package ao.wizenda.backend.handlers;

import ao.wizenda.backend.exceptions.WizaException;
import ao.wizenda.backend.handlers.models.ErrorData;
import ao.wizenda.backend.handlers.models.ErrorData.ErrorDetails;
import ao.wizenda.backend.utils.TranslationUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final TranslationUtils translator;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<@NonNull ErrorData> handleValidationException(MethodArgumentNotValidException e,
                                                                      HttpServletResponse response) {
    var errors = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> ErrorDetails.builder()
            .field(fieldError.getField())
            .error(translator.translate(fieldError.getDefaultMessage()))
            .build()
        )
        .collect(Collectors.toSet());

    var requestId = response.getHeader("sessionId");
    return ResponseEntity.badRequest().body(new ErrorData(
        400,
        "ValidationError",
        "VLD",
        errors,
        LocalDateTime.now(),
        requestId
    ));
  }

  @ExceptionHandler(WizaException.class)
  public ResponseEntity<@NonNull ErrorData> handleValidationException(WizaException e,
                                                                      HttpServletResponse response) {
    var errors = Set.of(
        ErrorDetails.builder()
            .code(e.getCode())
            .error(translator.translate(e.getMessage()))
            .build()
    );

    var requestId = response.getHeader("sessionId");
    return ResponseEntity.badRequest().body(new ErrorData(
        400,
        "ValidationError",
        "VLD",
        errors,
        LocalDateTime.now(),
        requestId
    ));
  }
}
