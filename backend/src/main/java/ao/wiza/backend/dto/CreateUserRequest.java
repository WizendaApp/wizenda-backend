package ao.wiza.backend.dto;

import ao.wiza.backend.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;

@NullMarked
public record CreateUserRequest(
    @NotNull String username,
    @NotNull @Email String email,
    @NotNull String name,
    @NotNull String phone,
    @NotNull @Past LocalDate birthDate,
    @NotNull Role role,
    @NotNull @Size(min = 6, max = 36) String password) {
}
