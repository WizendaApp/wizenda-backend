package ao.wizenda.backend.dto;

import ao.wizenda.backend.models.Role;

public record CreateUserResponse(String id, String username, Role role) {
}
