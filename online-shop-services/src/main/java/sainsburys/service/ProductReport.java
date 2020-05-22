
package sainsburys.service;

import java.util.List;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.model.Product;
import sainsburys.util.exception.ServiceException;

/**
 * Produces product report contents.
 */
public interface ProductReport {

  /**
   * Produces product report contents from the given products.
   * 
   * @param products
   *   the products to be included on the report.
   * @return
   *   the product report contents.
   * @throws ServiceException
   *   any exception during production of the report contents.
   */
  public ProductReportContents createReportContents(List<Product> products) throws ServiceException;

}
