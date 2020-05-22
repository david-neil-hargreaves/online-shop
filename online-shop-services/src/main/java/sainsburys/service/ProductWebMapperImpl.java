
package sainsburys.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sainsburys.model.Product;
import sainsburys.model.view.web.ProductView;
import sainsburys.util.NumberUtil;
import sainsburys.util.StringUtil;

@Service
public class ProductWebMapperImpl implements ProductWebMapper {

  public static final int SCALE = 2;

  @Autowired
  private NumberUtil numberUtil;

  @Override
  public Product mapFrom(ProductView productView) {
    Product product = new Product();
    product.setTitle(productView.getTitle());
    if (!(StringUtil.isBlank(productView.getKilocaloriesPer100Grams()))) {
      product.setKilocaloriesPer100Grams(
          numberUtil.stripNonNumericCharactersInteger(productView.getKilocaloriesPer100Grams()));
    }
    if (!(StringUtil.isBlank(productView.getUnitPrice()))) {
      product.setUnitPrice(
          numberUtil.stripNonNumericCharactersDecimal(productView.getUnitPrice()).setScale(SCALE));
    }
    product.setDescription(productView.getDescription());
    product.setSortOrderNumber(productView.getSortOrderNumber());
    return product;
  }

  @Override
  public List<Product> mapFrom(List<ProductView> productViews) {
    List<Product> products = new ArrayList<>();
    for (ProductView productView : productViews) {
      products.add(mapFrom(productView));
    }
    return products;
  }

}
