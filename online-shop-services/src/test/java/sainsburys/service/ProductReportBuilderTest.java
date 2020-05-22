
package sainsburys.service;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.test.TestData.FORMATTED_PRODUCT_REPORT;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.core.IsEqual;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.model.Product;
import sainsburys.model.view.web.ProductView;
import sainsburys.test.AbstractTest;
import sainsburys.util.exception.ServiceException;

public class ProductReportBuilderTest extends AbstractTest {

  private static final String SERVICE_EXCEPTION_MESSAGE = "Service exception message";

  @Mock
  private WebPageConnector mockWebPageConnector;

  @Mock
  private ProductWebPageParser mockProductWebPageParser;

  @Mock
  private ProductWebMapper mockProductWebMapper;

  @Mock
  private ProductReport mockProductReport;

  @Mock
  private ProductReportFormatter mockProductReportFormatter;

  @Mock
  private ReportOutputter mockReportOutputter;

  @InjectMocks
  private ProductReportBuilder productReportBuilder = new ProductReportBuilderImpl();

  @Mock
  private Document mockDocument;

  @Mock
  private ProductReportContents mockProductReportContents;

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void testPerform() throws ServiceException, JsonProcessingException {
    List<ProductView> productViews = new ArrayList<>();
    when(mockWebPageConnector.getDocument()).thenReturn(mockDocument);
    when(mockProductWebPageParser.parse(mockDocument)).thenReturn(productViews);
    List<Product> products = new ArrayList<>();
    when(mockProductWebMapper.mapFrom(productViews)).thenReturn(products);
    when(mockProductReport.createReportContents(products)).thenReturn(mockProductReportContents);
    when(mockProductReportFormatter.formatReport(mockProductReportContents))
        .thenReturn(FORMATTED_PRODUCT_REPORT);
    productReportBuilder.perform();
    verify(mockWebPageConnector, times(1)).getDocument();
    verify(mockProductWebPageParser, times(1)).parse(mockDocument);
    verify(mockProductWebMapper, times(1)).mapFrom(productViews);
    verify(mockProductReport, times(1)).createReportContents(products);
    verify(mockProductReportFormatter, times(1)).formatReport(mockProductReportContents);
    verify(mockReportOutputter, times(1)).outputReport(FORMATTED_PRODUCT_REPORT);
  }

  @Test
  public void testPerformServiceException() throws ServiceException, JsonProcessingException {
    List<ProductView> productViews = new ArrayList<>();
    when(mockWebPageConnector.getDocument()).thenReturn(mockDocument);
    when(mockProductWebPageParser.parse(mockDocument)).thenReturn(productViews);
    List<Product> products = new ArrayList<>();
    when(mockProductWebMapper.mapFrom(productViews)).thenReturn(products);
    when(mockProductReport.createReportContents(products)).thenReturn(mockProductReportContents);
    ServiceException serviceException = new ServiceException(SERVICE_EXCEPTION_MESSAGE);
    doThrow(serviceException).when(mockProductReport).createReportContents(products);
    expectedException.expect(serviceException.getClass());
    expectedException.expectMessage(serviceException.getMessage());
    expectedException.expectCause(IsEqual.equalTo(serviceException.getCause()));
    productReportBuilder.perform();
  }

}
