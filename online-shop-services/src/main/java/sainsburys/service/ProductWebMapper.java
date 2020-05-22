
package sainsburys.service;

import java.util.List;
import sainsburys.model.Product;
import sainsburys.model.view.web.ProductView;

/**
 * Maps from the Web view of products to the product model.
 */
public interface ProductWebMapper {

  /**
   * Maps from a Web product view to the product model.
   * 
   * @param productView
   *   the product Web view.
   * @return
   *   the product.
   */
  public Product mapFrom(ProductView productView);

  /**
   * Maps from Web product views to the product model.
   * 
   * @param productViews
   *   the product Web views.
   * @return
   *   the products.
   */
  public List<Product> mapFrom(List<ProductView> productViews);

}
