package ao.wiza.backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;

public record LoginRequest(@NotBlank @NonNull String username, @NotBlank @NonNull String password) {
}
