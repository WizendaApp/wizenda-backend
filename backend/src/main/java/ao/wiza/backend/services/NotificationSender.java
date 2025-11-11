package ao.wiza.backend.services;

import ao.wiza.backend.models.Notification;

public interface NotificationSender {
  void send(Notification notification);
}
