package ao.wizenda.backend.services;

import ao.wizenda.backend.dto.CreateUserRequest;
import ao.wizenda.backend.dto.CreateUserResponse;
import ao.wizenda.backend.dto.ResendVerifyTokenRequest;
import ao.wizenda.backend.dto.VerifyUserRequest;
import ao.wizenda.backend.models.User;
import org.jspecify.annotations.NonNull;

public interface UserService {
  @NonNull CreateUserResponse create(@NonNull CreateUserRequest request);

  @NonNull User getByUsername(@NonNull String username);

  User verifyUser(VerifyUserRequest request);

  void resendVerifyToken(@NonNull ResendVerifyTokenRequest request);
}
