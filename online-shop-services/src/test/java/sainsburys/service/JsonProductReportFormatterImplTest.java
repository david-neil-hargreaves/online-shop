
package sainsburys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.test.TestData.createProductReportContents;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.test.AbstractTest;
import sainsburys.util.exception.ServiceException;

public class JsonProductReportFormatterImplTest extends AbstractTest {

  private static final String ATTRIBUTE_FORMATTED_PRODUCT_REPORT = "Formatted product report";

  private static final Path RESOURCE_DIRECTORY = Paths.get("src", "test", "resources");

  private static final String EXPECTED_FORMATTED_REPORT_FILE_NAME = "formattedProductReport.json";

  private String expectedFormattedReport;

  private ProductReportFormatter productReportFormatter = new JsonProductReportFormatterImpl();

  @Mock
  ProductReportContents mockProductReportContents;

  @Before
  public void setup() throws IOException {
    initMocks(this);
    expectedFormattedReport = new String(
        Files.readAllBytes(
            Paths.get(RESOURCE_DIRECTORY + File.separator + EXPECTED_FORMATTED_REPORT_FILE_NAME)));
  }

  @Test
  public void testFormatReport() throws ServiceException {
    ProductReportContents productReportContents = createProductReportContents();
    String actualFormattedReport = productReportFormatter.formatReport(productReportContents);
    assertEquals(ATTRIBUTE_FORMATTED_PRODUCT_REPORT, expectedFormattedReport,
        actualFormattedReport);
  }

  @Test
  public void testFormatReportJsonProcessingException() throws ServiceException {
    expectedException.expect(ServiceException.class);
    expectedException.expectCause(IsInstanceOf.instanceOf(JsonProcessingException.class));
    productReportFormatter.formatReport(mockProductReportContents);
  }

}
