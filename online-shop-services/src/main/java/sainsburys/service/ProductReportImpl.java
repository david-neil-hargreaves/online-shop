
package sainsburys.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sainsbury.model.view.report.ProductReportContents;
import sainsbury.model.view.report.ProductReportSummary;
import sainsburys.model.Product;
import sainsburys.util.VatCalculator;
import sainsburys.util.exception.ServiceException;

@Service
public class ProductReportImpl implements ProductReport {

  @Autowired
  private VatCalculator vatCalculator;

  @Override
  public ProductReportContents createReportContents(List<Product> products)
      throws ServiceException {
    ProductReportSummary productReportSummary = new ProductReportSummary();
    for (Product product : products) {
      productReportSummary.addToGross(product.getUnitPrice());
    }
    productReportSummary.setVat(vatCalculator.calculateVat(productReportSummary.getGross()));
    Collections.sort(products);
    ProductReportContents productReportContents =
        new ProductReportContents(products, productReportSummary);
    return productReportContents;
  }

}
