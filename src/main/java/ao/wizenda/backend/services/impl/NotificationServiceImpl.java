package ao.wizenda.backend.services.impl;

import ao.wizenda.backend.models.Notification;
import ao.wizenda.backend.models.NotificationType;
import ao.wizenda.backend.repository.NotificationRepository;
import ao.wizenda.backend.services.NotificationSender;
import ao.wizenda.backend.services.NotificationService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
  private final NotificationRepository repository;
  private final NotificationSender smsSender;
  private final NotificationSender emailSender;

  public NotificationServiceImpl(NotificationRepository repository,
                                 @Qualifier("sms") NotificationSender smsSender,
                                 @Qualifier("email") NotificationSender emailSender) {
    this.repository = repository;
    this.smsSender = smsSender;
    this.emailSender = emailSender;
  }

  @Override
  public void send(@NonNull Notification notification) {

    if (notification.getType() == NotificationType.SMS) {
      smsSender.send(notification);
    } else if (notification.getType() == NotificationType.EMAIL) {
      emailSender.send(notification);
    }

    repository.save(notification);
  }
}
