package ao.wizenda.backend.events.producers;

import ao.wizenda.backend.events.Event;
import org.jspecify.annotations.NonNull;

public interface EventProducer {
  void publish(@NonNull Event event);
}
