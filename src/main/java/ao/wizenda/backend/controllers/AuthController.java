package ao.wizenda.backend.controllers;

import ao.wizenda.backend.dto.*;
import ao.wizenda.backend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService service;


  @PostMapping("login")
  public ResponseEntity<@NonNull Void> login(@Valid @RequestBody LoginRequest request) {
    var user = service.login(request);

    var tokenRequest = new CreateTokenRequest(request.username(), user.getRole());

    var accessToken = service.createToken(tokenRequest);
    var refreshToken = service.createRefreshToken(tokenRequest);

    return ResponseEntity.ok()
        .header("accessToken", accessToken)
        .header("refreshToken", refreshToken)
        .build();
  }

  @PostMapping("refresh")
  public ResponseEntity<@NonNull Void> refreshLogin(@Valid @RequestBody RefreshLoginRequest request) {
    var user = service.getUser(request.refreshToken());

    if (user == null) {
      throw new IllegalStateException();
    }

    var tokenRequest = new CreateTokenRequest(user.getUsername(), user.getRole());

    var accessToken = service.createToken(tokenRequest);
    var refreshToken = service.createRefreshToken(tokenRequest);

    return ResponseEntity.ok()
        .header("accessToken", accessToken)
        .header("refreshToken", refreshToken)
        .build();
  }
}
