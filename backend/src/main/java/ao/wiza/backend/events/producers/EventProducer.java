package ao.wiza.backend.events.producers;

import ao.wiza.backend.events.Event;
import org.jspecify.annotations.NonNull;

public interface EventProducer {
  void publish(@NonNull Event event);
}
