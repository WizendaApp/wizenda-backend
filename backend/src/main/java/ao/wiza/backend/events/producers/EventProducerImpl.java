package ao.wiza.backend.events.producers;

import ao.wiza.backend.events.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {
  private final ApplicationEventPublisher publisher;

  @Async("virtualThreadsAsyncExecutor")
  @Override
  public void publish(@NonNull Event event) {
    log.info("Publishing event {}, type {}", event, event.getClass());
    publisher.publishEvent(event);
  }
}
