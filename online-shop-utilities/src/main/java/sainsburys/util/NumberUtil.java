
package sainsburys.util;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provides methods to construct numbers from strings which include numbers and non-numeric
 * characters.
 */
@Service
public class NumberUtil {

  private static final String EMPTY_STRING = "";

  @Autowired
  @Value("${non-numeric-characters-regular-expression}")
  private String nonNumericCharactersRegularExpression;

  /**
   * Strips non-numeric characters from a string returning a BigDecimal. The characters to be
   * stripped are configurable and thus can respect the system locale.
   * 
   * @param number string which includes a number and non-numeric characters.
   * @return the number stripped of non-numeric characters.
   */
  public BigDecimal stripNonNumericCharactersDecimal(String number) {
    return new BigDecimal(stripNonNumericCharacters(number));
  }

  /**
   * Strips non-numeric characters from a string returning an Integer. The characters to be stripped
   * are configurable and thus can respect the system locale.
   * 
   * @param number string which includes a number and non-numeric characters.
   * @return the number stripped of non-numeric characters.
   */
  public Integer stripNonNumericCharactersInteger(String number) {
    return new Integer(stripNonNumericCharacters(number));
  }

  private String stripNonNumericCharacters(String number) {
    return number.replaceAll(nonNumericCharactersRegularExpression, EMPTY_STRING);
  }

  void setNonNumericCharactersRegularExpression(
      final String nonNumericCharactersRegularExpression) {
    this.nonNumericCharactersRegularExpression = nonNumericCharactersRegularExpression;
  }

}
