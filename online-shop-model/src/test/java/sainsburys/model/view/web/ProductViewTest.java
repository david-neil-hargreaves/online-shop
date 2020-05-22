
package sainsburys.model.view.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProductViewTest {

  private static final String ATTRIBUTE_TITLE = "Title";
  private static final String TITLE = "Cherries";

  @Test
  public void test() {
    ProductView productView = new ProductView(TITLE);
    assertEquals(ATTRIBUTE_TITLE, TITLE, productView.getTitle());
  }

}
