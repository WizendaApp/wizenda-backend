package ao.wizenda.backend.exceptions;

public class ResourceNotFoundException extends WizaException {
  public ResourceNotFoundException(String message) {
    super("NOT_FOUND", message);
  }
}
