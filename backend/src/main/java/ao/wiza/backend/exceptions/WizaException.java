package ao.wiza.backend.exceptions;

import lombok.Getter;

@Getter
public class WizaException extends RuntimeException {
  private final String code;

  public WizaException(String code, String message) {
    if (message != null && message.matches("[a-z]+(\\.[a-z])*")) {
      throw new IllegalArgumentException("Invalid message format!");
    }

    this.code = code;
    super(message);
  }
}
