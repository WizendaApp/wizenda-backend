package ao.wiza.backend.services;

import ao.wiza.backend.events.Event;
import org.jspecify.annotations.NonNull;

public interface EventService {
  void publish(@NonNull Event event);
}
