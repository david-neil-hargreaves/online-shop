
package sainsburys.util;

import static org.junit.Assert.assertEquals;
import static sainsburys.util.VatCalculator.MESSAGE_INVALID_PRECISION;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import sainsburys.test.AbstractTest;
import sainsburys.util.exception.ServiceException;

public class VatCalculatorTest extends AbstractTest {

  private static final BigDecimal VAT_RATE = new BigDecimal("20.00");
  private static final BigDecimal VAT_RATE_ZERO = new BigDecimal("0.00");
  private static final int PRECISION_6 = 6;
  private static final int PRECISION_0 = 0;
  private static final int SCALE_6 = 6;
  private static final int SCALE_2 = 2;
  private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
  private static final String ATTRIBUTE_VAT = "V.A.T.";
  private static final BigDecimal GROSS_1 = new BigDecimal("38.51");
  private static final BigDecimal GROSS_2 = new BigDecimal("38.49");
  private static final BigDecimal GROSS_3 = new BigDecimal("38.48");
  private static final BigDecimal GROSS_ZERO = new BigDecimal("0.00");
  private static final BigDecimal EXPECTED_VAT_GROSS_1_SCALE_6 = new BigDecimal("6.418330");
  private static final BigDecimal EXPECTED_VAT_GROSS_2_SCALE_6 = new BigDecimal("6.415000");
  private static final BigDecimal EXPECTED_VAT_GROSS_3_SCALE_6 = new BigDecimal("6.413330");
  private static final BigDecimal EXPECTED_VAT_GROSS_1_SCALE_2 = new BigDecimal("6.42");
  private static final BigDecimal EXPECTED_VAT_GROSS_2_SCALE_2 = new BigDecimal("6.42");
  private static final BigDecimal EXPECTED_VAT_GROSS_3_SCALE_2 = new BigDecimal("6.41");
  private static final BigDecimal EXPECTED_VAT_GROSS_ZERO = new BigDecimal("0.00");
  private static final BigDecimal EXPECTED_VAT_RATE_ZERO = new BigDecimal("0.00");
  private static final String ARITHMETIC_EXCEPTION_MESSAGE =
      "Non-terminating decimal expansion; no exact representable decimal result.";

  private VatCalculator vatCalculator = new VatCalculator();

  @Test
  public void testCalculateVatNoRounding() throws ServiceException {
    testCalculateVatSuccess(VAT_RATE, PRECISION_6, SCALE_6, ROUNDING_MODE, GROSS_1,
        EXPECTED_VAT_GROSS_1_SCALE_6);
    testCalculateVatSuccess(VAT_RATE, PRECISION_6, SCALE_6, ROUNDING_MODE, GROSS_2,
        EXPECTED_VAT_GROSS_2_SCALE_6);
    testCalculateVatSuccess(VAT_RATE, PRECISION_6, SCALE_6, ROUNDING_MODE, GROSS_3,
        EXPECTED_VAT_GROSS_3_SCALE_6);
  }

  @Test
  public void testCalculateVatRoundUp() throws ServiceException {
    testCalculateVatSuccess(VAT_RATE, PRECISION_6, SCALE_2, ROUNDING_MODE, GROSS_1,
        EXPECTED_VAT_GROSS_1_SCALE_2);
  }

  @Test
  public void testCalculateVatRoundUpFromHalf() throws ServiceException {
    testCalculateVatSuccess(VAT_RATE, PRECISION_6, SCALE_2, ROUNDING_MODE, GROSS_2,
        EXPECTED_VAT_GROSS_2_SCALE_2);
  }

  @Test
  public void testCalculateVatRoundDown() throws ServiceException {
    testCalculateVatSuccess(VAT_RATE, PRECISION_6, SCALE_2, ROUNDING_MODE, GROSS_3,
        EXPECTED_VAT_GROSS_3_SCALE_2);
  }

  @Test
  public void testCalculateVatGrossZero() throws ServiceException {
    testCalculateVatSuccess(VAT_RATE, PRECISION_0, SCALE_2, ROUNDING_MODE, GROSS_ZERO,
        EXPECTED_VAT_GROSS_ZERO);
  }

  @Test
  public void testCalculateVatRateZero() throws ServiceException {
    testCalculateVatSuccess(VAT_RATE_ZERO, PRECISION_6, SCALE_2, ROUNDING_MODE, GROSS_1,
        EXPECTED_VAT_RATE_ZERO);
  }

  private void testCalculateVatSuccess(BigDecimal vatRate, int precision, int scale,
      RoundingMode roundingMode, BigDecimal gross, BigDecimal expectedVat) throws ServiceException {
    vatCalculator.setVatRate(vatRate);
    vatCalculator.setPrecision(precision);
    vatCalculator.setScale(scale);
    vatCalculator.setRoundingModeName(roundingMode.name());
    vatCalculator.setup();
    BigDecimal actualVat = vatCalculator.calculateVat(gross);
    assertEquals(ATTRIBUTE_VAT, expectedVat, actualVat);
  }

  @Test
  public void testCalculateVatFailure() throws ServiceException {
    testCalculateVatFailure(VAT_RATE, PRECISION_0, SCALE_2, ROUNDING_MODE, GROSS_1);
  }

  private void testCalculateVatFailure(BigDecimal vatRate, int precision, int scale,
      RoundingMode roundingMode, BigDecimal gross) throws ServiceException {
    vatCalculator.setVatRate(vatRate);
    vatCalculator.setPrecision(precision);
    vatCalculator.setScale(scale);
    vatCalculator.setRoundingModeName(roundingMode.name());
    vatCalculator.setup();
    ArithmeticException arithmeticException = new ArithmeticException(ARITHMETIC_EXCEPTION_MESSAGE);
    ServiceException serviceException =
        new ServiceException(MESSAGE_INVALID_PRECISION, arithmeticException);
    expectedException.expect(serviceException.getClass());
    expectedException.expectMessage(serviceException.getMessage());
    expectedException.expectCause(IsInstanceOf.instanceOf(arithmeticException.getClass()));
    vatCalculator.calculateVat(gross);
  }

}
