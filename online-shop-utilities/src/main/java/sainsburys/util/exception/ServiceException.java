
package sainsburys.util.exception;

/** Represents a service exception. */
public class ServiceException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a service exception with the exception cause.
   * 
   * @param cause the cause of the service exception.
   */
  public ServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructs a service exception with the given message.
   * 
   * @param message the detailed message for the service exception.
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * Constructs a service exception with the given message and exception cause.
   * 
   * @param message the detailed message for the service exception.
   * @param cause the cause of the service exception.
   */
  public ServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
