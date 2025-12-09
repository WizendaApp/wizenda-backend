package ao.wizenda.backend.controllers;

import ao.wizenda.backend.controllers.docs.UserControllerDocs;
import ao.wizenda.backend.dto.*;
import ao.wizenda.backend.services.AuthService;
import ao.wizenda.backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.ofNullable;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {
  private final UserService service;
  private final AuthService authService;

  @PostMapping
  public ResponseEntity<@NonNull CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    var userCreated = service.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @GetMapping("me")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<@NonNull GetMeResponse> getCurrentUser() {
    var username = (String) ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .orElseThrow().getPrincipal();

    if (username == null) {
      throw new IllegalStateException();
    }

    var user = service.getByUsername(username);

    return ResponseEntity.ok(new GetMeResponse(
        user.getUsername(),
        user.getName(),
        user.getImageUrl(),
        user.getEmail(),
        user.getPhone()
    ));
  }

  @PostMapping("verify")
  public ResponseEntity<@NonNull Void> verifyUser(@Valid @RequestBody VerifyUserRequest request) {
    var user = service.verifyUser(request);

    var createTokenRequest = new CreateTokenRequest(user.getUsername(), user.getRole());

    var token = authService.createToken(createTokenRequest);
    var refreshToken = authService.createRefreshToken(createTokenRequest);

    return ResponseEntity.noContent()
        .header("accessToken", token)
        .header("refreshToken", refreshToken)
        .build();
  }

  @PostMapping("verify/resend")
  public ResponseEntity<@NonNull Void> resendVerifyToken(@Valid @RequestBody ResendVerifyTokenRequest request) {

    service.resendVerifyToken(request);

    return ResponseEntity.noContent().build();
  }
}
