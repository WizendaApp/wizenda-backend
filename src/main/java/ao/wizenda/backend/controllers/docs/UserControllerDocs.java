package ao.wizenda.backend.controllers.docs;

import ao.wizenda.backend.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerDocs {
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
