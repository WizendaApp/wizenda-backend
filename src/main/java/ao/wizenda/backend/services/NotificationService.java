package ao.wizenda.backend.services;

import ao.wizenda.backend.models.Notification;
import org.jspecify.annotations.NonNull;

public interface NotificationService {
  void send(@NonNull Notification notification);
}
