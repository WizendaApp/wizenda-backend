package ao.wiza.backend.exceptions;

import lombok.Getter;

@Getter
public class WizaException extends RuntimeException {
  private final String code;
  public WizaException(String code, String message) {
    super(message);
    this.code = code;
  }
}
