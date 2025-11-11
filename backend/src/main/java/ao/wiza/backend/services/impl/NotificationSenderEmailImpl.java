package ao.wiza.backend.services.impl;

import ao.wiza.backend.models.Notification;
import ao.wiza.backend.services.NotificationSender;
import org.springframework.stereotype.Service;

@Service("email")
public class NotificationSenderEmailImpl implements NotificationSender {
  @Override
  public void send(Notification notification) {

  }
}
