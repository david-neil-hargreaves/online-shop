
package sainsburys.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilTest {

  private static final String ATTRIBUTE_FIRST_LINE = "First line";
  private static final String ATTRIBUTE_IS_BLANK = "Is blank?";

  private static final String MULTI_LINE_STRING = "Cherries\r\n\r\nSucculent and sweet\r\n\r\n"
      + "1 of 5 a-day 10 cherries\r\n\r\nClass I";

  private static final String CARRIAGE_RETURN_STRING = "Cherries\r\rSucculent and sweet\r\r"
      + "1 of 5 a-day 10 cherries\r\rClass I";

  private static final String NEW_LINE_STRING = "Cherries\n\nSucculent and sweet\n\n"
      + "1 of 5 a-day 10 cherries\n\nClass I";

  private static final String LINE_SEPARATOR_STRING = "Cherries" + System.lineSeparator()
      + "Succulent and sweet" + System.lineSeparator()
      + "1 of 5 a-day 10 cherries" + System.lineSeparator() + "Class I";

  private static final String FIRST_LINE = "Cherries";
  private static final String SINGLE_LINE_STRING = "Raspberries";
  private static final String EMPTY_STRING = "";
  private static final String LINE_SEPARATOR_ONLY = System.lineSeparator();
  private static final String BLANK_STRING = "  ";

  @Test
  public void testGetFirstLineMultiLineString() {
    assertEquals(ATTRIBUTE_FIRST_LINE, FIRST_LINE,
        StringUtil.getFirstLine(MULTI_LINE_STRING));
  }

  @Test
  public void testGetFirstLineMultiLineStringCarriageReturnOnly() {
    assertEquals(ATTRIBUTE_FIRST_LINE, FIRST_LINE,
        StringUtil.getFirstLine(CARRIAGE_RETURN_STRING));
  }

  @Test
  public void testGetFirstLineMultiLineStringNewLineOnly() {
    assertEquals(ATTRIBUTE_FIRST_LINE, FIRST_LINE,
        StringUtil.getFirstLine(NEW_LINE_STRING));
  }

  @Test
  public void testGetFirstLineMultiLineStringLineSeparatorOnly() {
    assertEquals(ATTRIBUTE_FIRST_LINE, FIRST_LINE,
        StringUtil.getFirstLine(LINE_SEPARATOR_STRING));
  }

  @Test
  public void testGetFirstLineSingleLineString() {
    assertEquals(ATTRIBUTE_FIRST_LINE, SINGLE_LINE_STRING,
        StringUtil.getFirstLine(SINGLE_LINE_STRING));
  }

  @Test
  public void testGetFirstLineEmptyString() {
    assertEquals(ATTRIBUTE_FIRST_LINE, EMPTY_STRING,
        StringUtil.getFirstLine(EMPTY_STRING));
  }

  @Test
  public void testGetFirstLineLineSeparatorOnly() {
    assertEquals(ATTRIBUTE_FIRST_LINE, EMPTY_STRING,
        StringUtil.getFirstLine(LINE_SEPARATOR_ONLY));
  }

  @Test
  public void testGetFirstLineNull() {
    assertEquals(ATTRIBUTE_FIRST_LINE, null,
        StringUtil.getFirstLine(null));
  }

  @Test
  public void testIsBlankNullString() {
    assertEquals(ATTRIBUTE_IS_BLANK, true, StringUtil.isBlank(null));
  }

  @Test
  public void testIsBlankEmptyString() {
    assertEquals(ATTRIBUTE_IS_BLANK, true, StringUtil.isBlank(EMPTY_STRING));
  }

  @Test
  public void testIsBlankBlankString() {
    assertEquals(ATTRIBUTE_IS_BLANK, true, StringUtil.isBlank(BLANK_STRING));
  }

  @Test
  public void testIsBlankWhitespaceString() {
    assertEquals(ATTRIBUTE_IS_BLANK, true, StringUtil.isBlank(LINE_SEPARATOR_ONLY));
  }

  @Test
  public void testIsBlankNotBlankString() {
    assertEquals(ATTRIBUTE_IS_BLANK, false, StringUtil.isBlank(SINGLE_LINE_STRING));
  }

}
