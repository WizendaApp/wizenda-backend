package ao.wizenda.backend.dto;

import ao.wizenda.backend.models.Role;
import ao.wizenda.backend.utils.validations.adult.IsAdult;
import jakarta.validation.constraints.*;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;

@NullMarked
public record CreateUserRequest(
    @NotNull String username,
    @NotNull @Email String email,
    @NotNull String name,
    @NotNull @Pattern(regexp = "^9[1234579]\\d{7}$", message = "Invalid phone number") String phone,
    @NotNull @IsAdult LocalDate birthDate,
    @NotNull @Pattern(regexp = "^(CLIENT|ADMIN|PARTNER)$", message = "Invalid user role") Role role,
    @NotNull @Size(min = 6, max = 36) String password) {
}
