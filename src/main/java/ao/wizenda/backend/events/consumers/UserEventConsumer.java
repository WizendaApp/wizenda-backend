package ao.wizenda.backend.events.consumers;

import ao.wizenda.backend.events.SendNotificationEvent;
import ao.wizenda.backend.events.UserCreatedEvent;
import ao.wizenda.backend.events.producers.EventProducer;
import ao.wizenda.backend.exceptions.ResourceNotFoundException;
import ao.wizenda.backend.models.UserVerification;
import ao.wizenda.backend.repository.UserRepository;
import ao.wizenda.backend.repository.UserVerificationRepository;
import ao.wizenda.backend.utils.RandomUtils;
import ao.wizenda.backend.utils.TranslationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static ao.wizenda.backend.models.NotificationType.SMS;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {
  private final UserVerificationRepository userVerificationRepository;
  private final UserRepository userRepository;
  private final TranslationUtils translator;
  private final EventProducer eventProducer;

  @Async("virtualThreadsAsyncExecutor")
  @EventListener
  @Transactional
  public void handleUserCreatedEvent(@NonNull UserCreatedEvent event) {
    log.info("Finding user by username: {}", event.username());
    var user = userRepository.findByUsernameOrEmail(event.username());

    if (user == null) {
      log.warn("username no found: {}", event.username());
      throw new ResourceNotFoundException("username not found: " + event.username());
    }

    var userVerification = new UserVerification();
    userVerification.setUser(user);
    log.debug("Generating verification code...");
    var code = RandomUtils.randomCode(4);
    userVerification.setCode(code);
    log.debug("Verification code: {}", code);
    userVerification.setActive(true);
    userVerification.setCreatedAt(LocalDateTime.now());

    userVerificationRepository.save(userVerification);

    var lang = event.language();

    var notification = new SendNotificationEvent(
        "",
        user.getPhone(),
        translator.translate("user.confirmation.subtitle", lang),
        translator.translate("user.confirmation.message", lang, user.getName(), code),
        lang,
        SMS
    );

    eventProducer.publish(notification);
  }
}
