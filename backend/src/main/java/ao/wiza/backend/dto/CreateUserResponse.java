package ao.wiza.backend.dto;

import ao.wiza.backend.models.Role;

public record CreateUserResponse(String id, String username, Role role) {
}
