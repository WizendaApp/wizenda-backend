package ao.wiza.backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;

public record ResendVerifyTokenRequest(@NotBlank @NonNull String username) {
}
