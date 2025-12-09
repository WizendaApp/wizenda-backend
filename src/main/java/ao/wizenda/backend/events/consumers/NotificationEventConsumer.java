package ao.wizenda.backend.events.consumers;

import ao.wizenda.backend.events.SendNotificationEvent;
import ao.wizenda.backend.models.Notification;
import ao.wizenda.backend.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {
  private final NotificationService service;

  @Async("virtualThreadsAsyncExecutor")
  @EventListener
  @Transactional
  public void handleSendNotification(SendNotificationEvent event) {
    log.info("Receiving notification: {}", event);
    var notification = new Notification();
    notification.setTo(event.to());
    notification.setFrom(event.from());
    notification.setContent(event.content());
    notification.setType(event.type());
    notification.setSubject(event.subject());


    log.info("Sending notification...");
    service.send(notification);
    log.info("Notification sent...");
  }
}
