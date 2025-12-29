package ao.wizenda.backend.controllers.docs;

import ao.wizenda.backend.dto.LoginRequest;
import ao.wizenda.backend.dto.RefreshLoginRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Auth", description = "Set of Auth operations")
public interface AuthControllerDocs {

  @ApiResponse(
      responseCode = "200",
      description = "Login success",
      content = @Content(
          schema = @Schema,
          mediaType = APPLICATION_JSON_VALUE
      )
  )
  ResponseEntity<@NonNull Void> login(@Valid @RequestBody LoginRequest request);

  @ApiResponse(
      responseCode = "200",
      description = "Refresh Token success",
      content = @Content(
          schema = @Schema,
          mediaType = APPLICATION_JSON_VALUE
      )
  )
  ResponseEntity<@NonNull Void> refreshLogin(@Valid @RequestBody RefreshLoginRequest request);
}
