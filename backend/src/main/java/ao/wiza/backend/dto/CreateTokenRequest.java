package ao.wiza.backend.dto;

import ao.wiza.backend.models.Role;

public record CreateTokenRequest(String username, Role role) {
}
