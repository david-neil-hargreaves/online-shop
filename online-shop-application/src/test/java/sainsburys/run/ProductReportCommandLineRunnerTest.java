
package sainsburys.run;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.test.TestData.SERVICE_EXCEPTION_MESSAGE;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsburys.service.ProductReportBuilder;
import sainsburys.test.AbstractTest;
import sainsburys.test.TestAppender;
import sainsburys.util.exception.ServiceException;

public class ProductReportCommandLineRunnerTest extends AbstractTest {

  private static final String LOG_NAME = "testAppender";

  private static final int EXPECTED_NUMBER_OF_LOG_MESSAGES = 1;
  private static final String ATTRIBUTE_NUMBER_OF_LOG_MESSAGES = "Number of log messages";
  private static final String ATTRIBUTE_LOG_NUMBERED_MESSAGE = "Log message number %s";

  private static final String MESSAGE_TEST_APPENDER_SHOULD_NOT_BE_NULL =
      LOG_NAME + " should not be null.";

  private TestAppender testAppender;

  @Mock
  private ProductReportBuilder mockProductReportBuilder;

  @InjectMocks
  private ProductReportCommandLineRunner productReportCommandLineRunner =
      new ProductReportCommandLineRunner();

  @Before
  public void setup() {
    initMocks(this);
    LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
    Configuration configuration = loggerContext.getConfiguration();
    testAppender = (TestAppender) configuration.getAppenders().get(LOG_NAME);
    assertNotNull(MESSAGE_TEST_APPENDER_SHOULD_NOT_BE_NULL, testAppender);
    testAppender.setMessages(new ArrayList<String>());
  }

  @Test
  public void testRun() {
    String argument = null;
    productReportCommandLineRunner.run(argument);
    verifyNoLogMessages();
  }

  @Test
  public void testRunServiceException() throws ServiceException {
    ServiceException serviceException = new ServiceException(SERVICE_EXCEPTION_MESSAGE);
    doThrow(serviceException).when(mockProductReportBuilder).perform();
    String argument = null;
    productReportCommandLineRunner.run(argument);
    verify(serviceException.getMessage());
  }

  private void verify(String expectedLogMessage) {
    assertEquals(ATTRIBUTE_NUMBER_OF_LOG_MESSAGES, EXPECTED_NUMBER_OF_LOG_MESSAGES,
        testAppender.getMessages().size());
    int messageNumber = 1;
    for (String logMessage : testAppender.getMessages()) {
      String attribute = String.format(ATTRIBUTE_LOG_NUMBERED_MESSAGE, messageNumber);
      assertEquals(attribute, expectedLogMessage, logMessage);
      messageNumber++;
    }
  }

  private void verifyNoLogMessages() {
    assertEquals(ATTRIBUTE_NUMBER_OF_LOG_MESSAGES, 0, testAppender.getMessages().size());
  }

}
