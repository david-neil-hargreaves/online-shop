

package sainsburys.run;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import sainsburys.service.ProductReportBuilder;
import sainsburys.util.exception.ServiceException;

/**
 * Command line runner for the Online Shop application.
 */
@Service
public class ProductReportCommandLineRunner implements CommandLineRunner {

  private static final Logger LOGGER = LogManager.getLogger(ProductReportCommandLineRunner.class);

  @Autowired
  private ProductReportBuilder productReportBuilder;

  /**
   * Builds and outputs the product report.
   */
  @Override
  public void run(String... arguments) {
    LOGGER.traceEntry();
    try {
      productReportBuilder.perform();
    } catch (ServiceException exception) {
      LOGGER.error(exception.getMessage(), exception);
    }
    LOGGER.traceExit();
  }

}
