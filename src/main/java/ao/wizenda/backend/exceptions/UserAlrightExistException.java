package ao.wizenda.backend.exceptions;

public class UserAlrightExistException extends WizaException {
  public UserAlrightExistException(String user) {
    super("ALRIGHT_EXISTS", "User %s already exists".formatted(user));
  }
}
