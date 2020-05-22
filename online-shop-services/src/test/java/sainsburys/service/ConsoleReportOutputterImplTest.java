
package sainsburys.service;

import static sainsburys.test.TestData.FORMATTED_PRODUCT_REPORT;

import org.junit.Test;

public class ConsoleReportOutputterImplTest {

  private ReportOutputter reportOutputter = new ConsoleReportOutputterImpl();

  @Test
  public void testOutputReport() {
    reportOutputter.outputReport(FORMATTED_PRODUCT_REPORT);
  }
}
