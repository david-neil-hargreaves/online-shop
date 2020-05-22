
package sainsburys.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sainsbury.model.view.report.ProductReportContents;
import sainsburys.model.Product;
import sainsburys.model.view.web.ProductView;
import sainsburys.util.exception.ServiceException;

/**
 * Builds a product report.
 */
@Service
public class ProductReportBuilderImpl implements ProductReportBuilder {

  private static final Logger LOGGER = LogManager.getLogger(ProductReportBuilderImpl.class);

  @Autowired
  private WebPageConnector webPageConnector;

  @Autowired
  private ProductWebPageParser productWebPageParser;

  @Autowired
  private ProductWebMapper productWebMapper;

  @Autowired
  private ProductReport productReport;

  @Autowired
  private ProductReportFormatter productReportFormatter;

  @Autowired
  private ReportOutputter reportOutputter;

  /**
   * Builds a product report.
   * <li>Connects to a Web page.
   * <li>Parses the Web page, and linked pages, extracting product details.
   * <li>Builds a local product model.
   * <li>Builds a reporting view of the products.
   * <li>Formats the report.
   * <li>Outputs the report.
   * 
   * @throws ServiceException
   *   any exception during building of the product report.
   */
  @Override
  public void perform() throws ServiceException {
    LOGGER.traceEntry();
    Document document = webPageConnector.getDocument();
    List<ProductView> productViews = productWebPageParser.parse(document);
    List<Product> products = productWebMapper.mapFrom(productViews);
    ProductReportContents productReportContents = productReport.createReportContents(products);
    String formattedProductReport = productReportFormatter.formatReport(productReportContents);
    reportOutputter.outputReport(formattedProductReport);
    LOGGER.traceExit(formattedProductReport);
  }

}
