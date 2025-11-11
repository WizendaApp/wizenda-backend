package ao.wiza.backend.events;

public record UserCreatedEvent(String id, String username, String language) implements Event {
}
