package ao.wizenda.backend.handlers.models;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

public record ErrorData(int status, String error, String code, Set<ErrorDetails> details,
                        LocalDateTime timestamp, String requestId) {
  @Builder
  public record ErrorDetails(String field, String code, String error) {

  }
}
