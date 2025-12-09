package ao.wizenda.backend.dto;

import ao.wizenda.backend.models.Role;

public record CreateTokenRequest(String username, Role role) {
}
