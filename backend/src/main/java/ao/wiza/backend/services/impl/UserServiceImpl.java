package ao.wiza.backend.services.impl;

import ao.wiza.backend.dto.CreateUserRequest;
import ao.wiza.backend.dto.CreateUserResponse;
import ao.wiza.backend.dto.ResendVerifyTokenRequest;
import ao.wiza.backend.dto.VerifyUserRequest;
import ao.wiza.backend.events.SendNotificationEvent;
import ao.wiza.backend.events.UserCreatedEvent;
import ao.wiza.backend.exceptions.ResourceNotFoundException;
import ao.wiza.backend.models.User;
import ao.wiza.backend.repository.UserRepository;
import ao.wiza.backend.repository.UserVerificationRepository;
import ao.wiza.backend.events.producers.EventProducer;
import ao.wiza.backend.services.UserService;
import ao.wiza.backend.utils.TranslationUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static ao.wiza.backend.filters.LanguageFilter.LanguageContext.LANG;
import static ao.wiza.backend.models.NotificationType.SMS;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final UserVerificationRepository uVerifyRepository;
  private final PasswordEncoder passwordEncoder;
  private final EventProducer publisher;
  private final TranslationUtils translator;

  @Override
  public @NonNull CreateUserResponse create(@NonNull CreateUserRequest request) {
    var user = User.builder()
        .createdAt(LocalDateTime.now())
        .username(request.username())
        .name(request.name())
        .phone(request.phone())
        .email(request.email())
        .role(request.role())
        .password(passwordEncoder.encode(request.password()))
        .build();


    user = repository.save(user);

    publisher.publish(new UserCreatedEvent(user.getId(), user.getUsername(), LANG.get()));

    return new CreateUserResponse(user.getId(), user.getUsername(), user.getRole());
  }

  @Override
  public @NonNull User getByUsername(@NonNull String username) {
    var user = repository.findByUsernameOrEmail(username);

    if (user == null) {
      throw new IllegalStateException("User not found");
    }

    return user;
  }

  @Override
  public User verifyUser(VerifyUserRequest request) {
    var user = repository.findByUsernameOrEmail(request.username());

    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }

    if (user.isActive()) {
      throw new IllegalStateException("User is alright verified");
    }

    var verification = uVerifyRepository.findValidCode(user.getId(), request.code());

    if (verification == null) {
      throw new ResourceNotFoundException("Verification Code not found");
    }

    user.setActive(true);

    verification.setActive(false);

    uVerifyRepository.save(verification);

    repository.save(user);

    return user;
  }

  @Override
  public void resendVerifyToken(@NonNull ResendVerifyTokenRequest request) {
    var user = repository.findByUsernameOrEmail(request.username());

    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }

    if (user.isActive()) {
      throw new IllegalStateException("User is alright verified");
    }

    var verification = uVerifyRepository.findValidCode(user.getId());

    if (verification == null) {
      throw new ResourceNotFoundException("error");
    }

    var lang = LANG.get();

    publisher.publish(new SendNotificationEvent(
        "",
        user.getPhone(),
        translator.translate("user.confirmation.subtitle", lang),
        translator.translate("user.confirmation.message", lang, user.getUsername(), verification.getCode()),
        lang,
        SMS
    ));
  }
}
