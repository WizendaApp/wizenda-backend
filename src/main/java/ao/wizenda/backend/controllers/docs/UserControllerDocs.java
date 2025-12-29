package ao.wizenda.backend.controllers.docs;

import ao.wizenda.backend.dto.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Users", description = "A set of User operations")
public interface UserControllerDocs {
  @ApiResponse(
      responseCode = "201",
      description = "User created",
      content = @Content(
          schema = @Schema(implementation = CreateUserResponse.class),
          mediaType = APPLICATION_JSON_VALUE
      )
  )
  @PostMapping
  ResponseEntity<@NonNull CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request);

  @GetMapping("me")
  @SecurityRequirement(name = "Bearer Authentication")
  ResponseEntity<@NonNull GetMeResponse> getCurrentUser();

  @PostMapping("verify")
  ResponseEntity<@NonNull Void> verifyUser(@Valid @RequestBody VerifyUserRequest request);


  @PostMapping("verify/resend")
  ResponseEntity<@NonNull Void> resendVerifyToken(@Valid @RequestBody ResendVerifyTokenRequest request);
}
