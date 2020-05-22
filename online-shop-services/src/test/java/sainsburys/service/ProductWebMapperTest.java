
package sainsburys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.test.TestData.KILOCALORIES_PER_100_GRAMS;
import static sainsburys.test.TestData.KILOCALORIES_PER_100_GRAMS_2;
import static sainsburys.test.TestData.STRING_KILOCALORIES_PER_100_GRAMS;
import static sainsburys.test.TestData.STRING_KILOCALORIES_PER_100_GRAMS_2;
import static sainsburys.test.TestData.STRING_UNIT_PRICE;
import static sainsburys.test.TestData.STRING_UNIT_PRICE_2;
import static sainsburys.test.TestData.UNIT_PRICE;
import static sainsburys.test.TestData.UNIT_PRICE_2;
import static sainsburys.test.TestData.createProduct;
import static sainsburys.test.TestData.createProductView;
import static sainsburys.test.TestData.createProductViews;
import static sainsburys.test.TestData.createProducts;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsburys.model.Product;
import sainsburys.model.view.web.ProductView;
import sainsburys.util.NumberUtil;

public class ProductWebMapperTest {

  private static final String ATTRIBUTE_PRODUCT = "Product";
  private static final String ATTRIBUTE_PRODUCTS = "Products";

  @Mock
  private NumberUtil mockNumberUtil;

  @InjectMocks
  private ProductWebMapper productWebMapper = new ProductWebMapperImpl();

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void testMapFrom() {
    when(mockNumberUtil.stripNonNumericCharactersInteger(STRING_KILOCALORIES_PER_100_GRAMS))
        .thenReturn(KILOCALORIES_PER_100_GRAMS);
    when(mockNumberUtil.stripNonNumericCharactersDecimal(STRING_UNIT_PRICE)).thenReturn(UNIT_PRICE);
    ProductView productView = createProductView();
    Product actualProduct = productWebMapper.mapFrom(productView);
    Product expectedProduct = createProduct();
    assertEquals(ATTRIBUTE_PRODUCT, expectedProduct, actualProduct);
    verify(mockNumberUtil, times(1))
        .stripNonNumericCharactersInteger(STRING_KILOCALORIES_PER_100_GRAMS);
    verify(mockNumberUtil, times(1)).stripNonNumericCharactersDecimal(STRING_UNIT_PRICE);
  }

  @Test
  public void testMapFromNulls() {
    ProductView productView = createProductView();
    productView.setKilocaloriesPer100Grams(null);
    productView.setUnitPrice(null);
    Product actualProduct = productWebMapper.mapFrom(productView);
    Product expectedProduct = createProduct();
    expectedProduct.setKilocaloriesPer100Grams(null);
    expectedProduct.setUnitPrice(null);
    assertEquals(ATTRIBUTE_PRODUCT, expectedProduct, actualProduct);
    verify(mockNumberUtil, never()).stripNonNumericCharactersInteger(any());
    verify(mockNumberUtil, never()).stripNonNumericCharactersDecimal(any());
  }

  @Test
  public void testMapFromList() {
    when(mockNumberUtil.stripNonNumericCharactersInteger(STRING_KILOCALORIES_PER_100_GRAMS))
        .thenReturn(KILOCALORIES_PER_100_GRAMS);
    when(mockNumberUtil.stripNonNumericCharactersDecimal(STRING_UNIT_PRICE)).thenReturn(UNIT_PRICE);
    when(mockNumberUtil.stripNonNumericCharactersInteger(STRING_KILOCALORIES_PER_100_GRAMS_2))
        .thenReturn(KILOCALORIES_PER_100_GRAMS_2);
    when(mockNumberUtil.stripNonNumericCharactersDecimal(STRING_UNIT_PRICE_2))
        .thenReturn(UNIT_PRICE_2);
    List<ProductView> productViews = createProductViews();
    List<Product> actualProducts = productWebMapper.mapFrom(productViews);
    List<Product> expectedProducts = createProducts();
    assertEquals(ATTRIBUTE_PRODUCTS, expectedProducts, actualProducts);
    verify(mockNumberUtil, times(1))
        .stripNonNumericCharactersInteger(STRING_KILOCALORIES_PER_100_GRAMS);
    verify(mockNumberUtil, times(1)).stripNonNumericCharactersDecimal(STRING_UNIT_PRICE);
    verify(mockNumberUtil, times(1))
        .stripNonNumericCharactersInteger(STRING_KILOCALORIES_PER_100_GRAMS_2);
    verify(mockNumberUtil, times(1)).stripNonNumericCharactersDecimal(STRING_UNIT_PRICE_2);
  }

}
