package ao.wiza.backend.services;

import ao.wiza.backend.models.Notification;
import org.jspecify.annotations.NonNull;

public interface NotificationService {
  void send(@NonNull Notification notification);
}
