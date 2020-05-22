
package sainsburys.model.view.web;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Represents a view of a product.
 */
@Data
public class ProductView {

  @Setter(AccessLevel.NONE)
  private String title;

  private String kilocaloriesPer100Grams;

  private String unitPrice;

  private String description;

  private Integer sortOrderNumber;

  /**
   * Constructs a product view with the given title.
   * 
   * @param title the product title.
   */
  public ProductView(String title) {
    this.title = title;
  }
}
