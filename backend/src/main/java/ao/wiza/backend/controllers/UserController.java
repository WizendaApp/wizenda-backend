package ao.wiza.backend.controllers;

import ao.wiza.backend.dto.*;
import ao.wiza.backend.services.AuthService;
import ao.wiza.backend.services.UserService;
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
public class UserController {
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

    return ResponseEntity.ok(new GetMeResponse());
  }

  @PostMapping("verify")
  public ResponseEntity<?> verifyUser(@Valid @RequestBody VerifyUserRequest request) {
    var user = service.verifyUser(request);

    var createTokenRequest = new CreateTokenRequest(user.getUsername(), user.getRole());

    var token = authService.createToken(createTokenRequest);
    var refreshToken = authService.createRefreshToken(createTokenRequest);

    return ResponseEntity.ok()
        .header("accessToken", token)
        .header("refreshToken", refreshToken)
        .body(new VerifyUserResponse());
  }
}
