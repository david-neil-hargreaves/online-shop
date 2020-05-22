
package sainsburys.util;

/**
 * Provides utility methods for strings.
 */
public class StringUtil {

  public static final String NEW_LINE = "\n";

  public static final String CARRIAGE_RETURN = "\r";

  private StringUtil() {}

  /**
   * Returns the first line of the provided text, in other words the start of the text up to the
   * first line separator character whether that be a Linux / Unix type or a Windows type line
   * separator.
   * 
   * @param text the text.
   * @return the first line of the text.
   */
  public static String getFirstLine(String text) {
    if (text == null) {
      return text;
    }
    int firstLineSeparator = text.indexOf(System.lineSeparator());
    if (firstLineSeparator == -1) {
      firstLineSeparator = text.indexOf(NEW_LINE);
    }
    if (firstLineSeparator == -1) {
      firstLineSeparator = text.indexOf(CARRIAGE_RETURN);
    }
    if (firstLineSeparator == -1) {
      return text;
    } else {
      return text.substring(0, firstLineSeparator);
    }
  }

  /**
   * Determines whether or not a string is blank i.e. is null or is empty when trimmed of whitespace
   * characters.
   * 
   * @param string the string.
   * @return true if the string is null or is empty when trimmed of whitespace characters, otherwise
   *         returns false.
   */
  public static boolean isBlank(String string) {
    return string == null || string.trim().isEmpty();
  }

}
