
package sainsburys.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sainsburys.util.exception.ServiceException;

/**
 * Calculates V.A.T. (Value Added Tax) for a gross amount.
 */
@Service
public class VatCalculator {

  public static final String MESSAGE_INVALID_PRECISION =
      "Property vat-calculation-precision should be set to a value higher than zero.";

  private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

  @Autowired
  @Value("${vat-rate}")
  private BigDecimal vatRate;

  @Autowired
  @Value("${vat-calculation-precision}")
  private int precision;

  @Autowired
  @Value("${vat-calculation-scale}")
  private int scale;

  @Autowired
  @Value("${vat-calculation-rounding-mode}")
  private String roundingModeName;

  private RoundingMode roundingMode;

  private MathContext mathsContext;

  private BigDecimal vatRatePlus100;

  @PostConstruct
  void setup() {
    roundingMode = RoundingMode.valueOf(roundingModeName.trim());
    mathsContext = new MathContext(precision, roundingMode);
    vatRatePlus100 = ONE_HUNDRED.add(vatRate);
  }

  /**
   * Calculates V.A.T. for the gross amount. The V.A.T. rate, precision, scale and rounding mode to
   * use in the calculation are all configurable.
   * 
   * @param gross the gross amount.
   * @return the V.A.T. calculated for the gross amount.
   * @throws ServiceException exception during the V.A.T. calculation.
   */
  public BigDecimal calculateVat(BigDecimal gross) throws ServiceException {
    try {
      return gross.multiply(vatRate).divide(vatRatePlus100, mathsContext).setScale(scale,
          roundingMode);
    } catch (ArithmeticException arithmeticException) {
      throw new ServiceException(MESSAGE_INVALID_PRECISION, arithmeticException);
    }
  }

  void setVatRate(BigDecimal vatRate) {
    this.vatRate = vatRate;
  }

  void setPrecision(int precision) {
    this.precision = precision;
  }

  void setScale(int scale) {
    this.scale = scale;
  }

  void setRoundingModeName(String roundingModeName) {
    this.roundingModeName = roundingModeName;
  }

}
