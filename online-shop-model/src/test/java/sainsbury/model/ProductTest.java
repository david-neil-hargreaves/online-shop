
package sainsbury.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import sainsburys.model.Product;

public class ProductTest {

  private static final String ATTRIBUTE_DESCRIPTION_FIRST_LINE = "Description first line";
  private static final String ATTRIBUTE_PRODUCT = "Product";
  private static final String DESCRIPTION = "Cherries" + System.lineSeparator()
      + "Succulent and sweet";
  private static final String DESCRIPTION_FIRST_LINE = "Cherries";
  private static final int SORT_ORDER_NUMBER_1 = 1;
  private static final int SORT_ORDER_NUMBER_2 = 2;

  @Test
  public void testGetDescriptionFirstLine() {
    Product product = new Product();
    product.setDescription(DESCRIPTION);
    assertEquals(ATTRIBUTE_DESCRIPTION_FIRST_LINE, DESCRIPTION_FIRST_LINE,
        product.getDescriptionFirstLine());
  }

  @Test
  public void testCompareTo() {
    Product product2 = new Product();
    product2.setSortOrderNumber(SORT_ORDER_NUMBER_2);
    Product product1 = new Product();
    product1.setSortOrderNumber(SORT_ORDER_NUMBER_1);
    List<Product> products = new ArrayList<>();
    products.add(product2);
    products.add(product1);
    Collections.sort(products);
    assertEquals(ATTRIBUTE_PRODUCT, product1, products.get(0));
    assertEquals(ATTRIBUTE_PRODUCT, product2, products.get(1));
    assertNotEquals(ATTRIBUTE_PRODUCT, product1, products.get(1));
    assertNotEquals(ATTRIBUTE_PRODUCT, product2, products.get(0));
  }

}
