
package sainsburys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.test.TestData.GROSS;
import static sainsburys.test.TestData.SERVICE_EXCEPTION_MESSAGE;
import static sainsburys.test.TestData.VAT;
import static sainsburys.test.TestData.createProductReportContents;
import static sainsburys.test.TestData.createProducts;

import java.util.List;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.model.Product;
import sainsburys.test.AbstractTest;
import sainsburys.util.VatCalculator;
import sainsburys.util.exception.ServiceException;

public class ProductReportTest extends AbstractTest {

  private static final String ATTRIBUTE_PRODUCT_REPORT_CONTENTS = "Product report contents";

  @Mock
  private VatCalculator mockVatCalculator;

  @InjectMocks
  private ProductReport productReport = new ProductReportImpl();

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void testCreateReportContents() throws ServiceException {
    when(mockVatCalculator.calculateVat(GROSS)).thenReturn(VAT);
    List<Product> products = createProducts();
    ProductReportContents actualProductReportContents =
        productReport.createReportContents(products);
    ProductReportContents expectedProductReportContents = createProductReportContents();
    assertEquals(ATTRIBUTE_PRODUCT_REPORT_CONTENTS, expectedProductReportContents,
        actualProductReportContents);
    verify(mockVatCalculator, times(1)).calculateVat(GROSS);
  }

  @Test
  public void testCreateReportContentsServiceException() throws ServiceException {
    ServiceException serviceException = new ServiceException(SERVICE_EXCEPTION_MESSAGE);
    when(mockVatCalculator.calculateVat(GROSS)).thenThrow(serviceException);
    expectedException.expect(serviceException.getClass());
    expectedException.expectMessage(serviceException.getMessage());
    expectedException.expectCause(IsEqual.equalTo(null));
    List<Product> products = createProducts();
    productReport.createReportContents(products);
  }

}
