
package sainsbury.model.view.report;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import org.junit.Test;

public class ProductSummaryTest {

  private static final String ATTRIBUTE_GROSS = "Gross";

  private static final BigDecimal UNIT_PRICE_1 = new BigDecimal("1.23");
  private static final BigDecimal UNIT_PRICE_2 = new BigDecimal("1.2");
  private static final BigDecimal UNIT_PRICE_3 = new BigDecimal("1");
  private static final BigDecimal UNIT_PRICE_4 = new BigDecimal("0");
  private static final BigDecimal EXPECTED_GROSS_1 = new BigDecimal("3.43");
  private static final BigDecimal EXPECTED_GROSS_2 = new BigDecimal("2.00");

  @Test
  public void testAddToGross() {
    ProductReportSummary productSummary = new ProductReportSummary();
    productSummary.addToGross(UNIT_PRICE_1);
    productSummary.addToGross(UNIT_PRICE_2);
    productSummary.addToGross(UNIT_PRICE_3);
    productSummary.addToGross(UNIT_PRICE_4);
    assertEquals(ATTRIBUTE_GROSS, EXPECTED_GROSS_1, productSummary.getGross());
  }

  @Test
  public void testAddToGrossNoDecimalPlaces() {
    ProductReportSummary productSummary = new ProductReportSummary();
    productSummary.addToGross(UNIT_PRICE_3);
    productSummary.addToGross(UNIT_PRICE_3);
    assertEquals(ATTRIBUTE_GROSS, EXPECTED_GROSS_2, productSummary.getGross());
  }

}
