package ao.wizenda.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

public record TelcoSMSRequest(
    Message message
) {
  public record Message(
      @NonNull @JsonProperty("api_key_app") String apiKey,
      @NonNull @JsonProperty("phone_number") String phone,
      @NonNull @JsonProperty("message_body") String body) {
  }
}
