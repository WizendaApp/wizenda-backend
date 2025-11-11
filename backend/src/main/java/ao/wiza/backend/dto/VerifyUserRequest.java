package ao.wiza.backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;

public record VerifyUserRequest(
    @NotBlank @NonNull String username,
    @NotBlank @NonNull String code) {
}
