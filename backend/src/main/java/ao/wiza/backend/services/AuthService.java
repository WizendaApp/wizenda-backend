package ao.wiza.backend.services;

import ao.wiza.backend.dto.CreateTokenRequest;
import ao.wiza.backend.dto.LoginRequest;
import ao.wiza.backend.models.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface AuthService {
  @Nullable User getUser(@NonNull String token);

  @NonNull String createToken(@NonNull CreateTokenRequest request);
  @NonNull String createRefreshToken(@NonNull CreateTokenRequest request);

  @NonNull String refreshToken(@NonNull String token);

  boolean validateToken(@NonNull String token);

  void revokeToken(@NonNull String token);

  User login(@NonNull LoginRequest request);
}
