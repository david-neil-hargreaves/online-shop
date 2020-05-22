
package sainsburys;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Spring Boot Online Shop application.
 */
@SpringBootApplication
public class ConsoleApplication {

  private static final Logger LOGGER = LogManager.getLogger(ConsoleApplication.class);

  /**
   * Runs the Spring Boot Online Shop application.
   * 
   * @param arguments the arguments - none are required.
   */
  public static void main(String[] arguments) {
    LOGGER.traceEntry();
    SpringApplication.run(ConsoleApplication.class, arguments);
    System.exit(0);
    LOGGER.traceExit();
  }

}
