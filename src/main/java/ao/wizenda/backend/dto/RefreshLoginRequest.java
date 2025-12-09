package ao.wizenda.backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;

public record RefreshLoginRequest(@NotBlank @NonNull String refreshToken) {
}
