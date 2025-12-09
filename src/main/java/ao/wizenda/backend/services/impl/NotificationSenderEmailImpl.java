package ao.wizenda.backend.services.impl;

import ao.wizenda.backend.models.Notification;
import ao.wizenda.backend.services.NotificationSender;
import org.springframework.stereotype.Service;

@Service("email")
public class NotificationSenderEmailImpl implements NotificationSender {
  @Override
  public void send(Notification notification) {

  }
}
