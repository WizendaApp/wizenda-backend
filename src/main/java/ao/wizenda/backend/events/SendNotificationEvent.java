package ao.wizenda.backend.events;

import ao.wizenda.backend.models.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;

public record SendNotificationEvent(
    @NotBlank @NonNull String from,
    @NotBlank @NonNull String to,
    @NotBlank @NonNull String subject,
    @NotBlank @NonNull String content,
    @NotBlank @NonNull String language,
    @NotNull @NonNull NotificationType type) implements Event {
}
