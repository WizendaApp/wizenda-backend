package ao.wizenda.backend.services;

import ao.wizenda.backend.models.Notification;

public interface NotificationSender {
  void send(Notification notification);
}
