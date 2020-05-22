
package sainsburys.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.run.ProductReportCommandLineRunner;

@RunWith(SpringRunner.class)
public class ProductReportCommandLineRunnerIT {

  private static final String ATTRIBUTE_PRODUCT_REPORT = "Product report";

  private static final Path RESOURCE_DIRECTORY = Paths.get("src", "test", "resources");

  private static final String EXPECTED_PRODUCT_REPORT_FILE_NAME = "expectedProductReport.json";

  private String expectedProductReport;

  @Mock
  ProductReportContents mockProductReportContents;

  @Before
  public void setup() throws IOException {
    initMocks(this);
    expectedProductReport = new String(
        Files.readAllBytes(
            Paths.get(RESOURCE_DIRECTORY + File.separator + EXPECTED_PRODUCT_REPORT_FILE_NAME)));
  }

  @Autowired
  private ProductReportCommandLineRunner productReportCommandLineRunner;

  @Test
  public void testRun() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(byteArrayOutputStream);
    System.setOut(printStream);
    String[] arguments = null;
    productReportCommandLineRunner.run(arguments);
    String actualProductReport = byteArrayOutputStream.toString();
    assertEquals(ATTRIBUTE_PRODUCT_REPORT, expectedProductReport, actualProductReport);
  }

  @SpringBootConfiguration
  @PropertySource(value = "classpath:online-shop.properties")
  @ComponentScan(basePackages = "sainsburys")
  public static class IntegrationTestEnvironmentConfiguration {
  }

}
