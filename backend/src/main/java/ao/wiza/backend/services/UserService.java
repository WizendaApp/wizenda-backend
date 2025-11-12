package ao.wiza.backend.services;

import ao.wiza.backend.dto.CreateUserRequest;
import ao.wiza.backend.dto.CreateUserResponse;
import ao.wiza.backend.dto.ResendVerifyTokenRequest;
import ao.wiza.backend.dto.VerifyUserRequest;
import ao.wiza.backend.models.User;
import org.jspecify.annotations.NonNull;

public interface UserService {
  @NonNull CreateUserResponse create(@NonNull CreateUserRequest request);

  @NonNull User getByUsername(@NonNull String username);

  User verifyUser(VerifyUserRequest request);

  void resendVerifyToken(@NonNull ResendVerifyTokenRequest request);
}
