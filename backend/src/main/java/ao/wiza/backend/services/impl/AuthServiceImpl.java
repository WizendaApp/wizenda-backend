package ao.wiza.backend.services.impl;

import ao.wiza.backend.dto.CreateTokenRequest;
import ao.wiza.backend.dto.LoginRequest;
import ao.wiza.backend.models.User;
import ao.wiza.backend.repository.UserRepository;
import ao.wiza.backend.security.TokenProvider;
import ao.wiza.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final TokenProvider tokenProvider;
  private final UserRepository repository;
  private final RedisTemplate<String, String> template;
  private final AuthenticationManager authManager;
  private static final String PREFIX = "wiza:token:";
  private static final String REFRESH_PREFIX = "wiza:refresh-token:";


  @Value("${security.jwt.token-expiry-time-in-seconds}")
  private long tokenExpireInSeconds;
  @Value("${security.jwt.refresh-expiry-time-in-seconds}")
  private long refreshExpiryInSeconds;

  @Override
  public @Nullable User getUser(@NonNull String token) {
    var username = ofNullable(tokenProvider.getAuthentication(token).getPrincipal())
        .map(Object::toString)
        .orElseThrow(() -> new IllegalArgumentException("Username cannot be null"));

    return repository.findByUsernameOrEmail(username);
  }

  @Override
  public @NonNull String createToken(@NonNull CreateTokenRequest request) {
    var token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(
        request.username(),
        "",
        List.of(new SimpleGrantedAuthority("ROLE_" + request.role()))));

    template.opsForValue().set(PREFIX + request.username(), token, Duration.ofSeconds(tokenExpireInSeconds));

    return token;
  }

  @Override
  public @NonNull String createRefreshToken(@NonNull CreateTokenRequest request) {
    var token = tokenProvider.createRefreshToken(new UsernamePasswordAuthenticationToken(request.username(), ""));

    template.opsForValue().set(REFRESH_PREFIX + request.username(), token, Duration.ofSeconds(refreshExpiryInSeconds));

    return token;
  }

  @Override
  public @NonNull String refreshToken(@NonNull String token) {
    var user = getUser(token);

    if (user == null) {
      throw new RuntimeException("");
    }

    revokeToken(token);

    return createToken(new CreateTokenRequest(user.getUsername(), user.getRole()));
  }

  @Override
  public boolean validateToken(@NonNull String token) {
    var user = getUser(token);

    if (user == null) return false;

    var exists = template.hasKey(PREFIX + user.getUsername());
    return tokenProvider.validateToken(token) && exists;
  }

  @Override
  public void revokeToken(@NonNull String token) {
    if (template.delete(PREFIX + token)) {
      System.out.println("Token deleted: " + token);
    }
  }

  @Override
  public User login(@NonNull LoginRequest request) {
    try {
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.username(), request.password())
      );
    } catch (Exception e) {
      log.error("e: ", e);
    }

    return repository.findByUsernameOrEmail(request.username());
  }
}
