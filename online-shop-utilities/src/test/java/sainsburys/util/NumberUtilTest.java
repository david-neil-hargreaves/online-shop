
package sainsburys.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import org.junit.Test;

public class NumberUtilTest {

  private static final String NON_NUMERIC_CHARACTERS_REGULAR_EXPRESSION = "[^\\d.]";
  private static final String ATTRIBUTE_NUMBER = "Number";
  private static final String DECIMAL_STRING = "£1.75,";
  private static final String INTEGER_STRING = "Energy 33kcal";
  private static final BigDecimal DECIMAL = new BigDecimal("1.75");
  private static final Integer INTEGER = new Integer("33");

  private NumberUtil numberUtil = new NumberUtil();
  
  {
    numberUtil.setNonNumericCharactersRegularExpression(NON_NUMERIC_CHARACTERS_REGULAR_EXPRESSION);
  }

  @Test
  public void testStripNonNumericCharactersDecimal() {
    assertEquals(ATTRIBUTE_NUMBER, DECIMAL,
        numberUtil.stripNonNumericCharactersDecimal(DECIMAL_STRING));
  }

  @Test
  public void testStripNonNumericCharactersInteger() {
    assertEquals(ATTRIBUTE_NUMBER, INTEGER,
        numberUtil.stripNonNumericCharactersInteger(INTEGER_STRING));
  }

}
