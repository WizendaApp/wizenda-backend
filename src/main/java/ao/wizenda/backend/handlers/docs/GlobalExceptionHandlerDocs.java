package ao.wizenda.backend.handlers.docs;


import ao.wizenda.backend.exceptions.WizaException;
import ao.wizenda.backend.handlers.models.ErrorData;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface GlobalExceptionHandlerDocs {

  @ApiResponse(
      responseCode = "400",
      description = "Internal Server Error",
      content = @Content(
          schema = @Schema(implementation = ErrorData.class),
          mediaType = APPLICATION_JSON_VALUE
      )
  )
  ResponseEntity<@NonNull ErrorData> handleValidationException(MethodArgumentNotValidException e,
                                                               HttpServletResponse response);


  @ApiResponse(
      responseCode = "400",
      description = "Bad Request",
      content = @Content(
          schema = @Schema(implementation = ErrorData.class),
          mediaType = APPLICATION_JSON_VALUE
      )
  )
  @ApiResponse(
      responseCode = "500",
      description = "Internal Server Error",
      content = @Content(
          schema = @Schema(implementation = ErrorData.class),
          mediaType = APPLICATION_JSON_VALUE
      )
  )
  ResponseEntity<@NonNull ErrorData> handleValidationException(WizaException e,
                                                               HttpServletResponse response);
}
