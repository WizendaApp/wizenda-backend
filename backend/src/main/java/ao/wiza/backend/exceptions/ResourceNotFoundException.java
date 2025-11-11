package ao.wiza.backend.exceptions;

public class ResourceNotFoundException extends WizaException {
  public ResourceNotFoundException(String message) {
    super("4004", message);
  }
}
