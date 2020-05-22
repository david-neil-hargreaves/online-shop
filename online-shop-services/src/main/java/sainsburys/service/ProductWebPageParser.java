
package sainsburys.service;

import java.util.List;
import org.jsoup.nodes.Document;
import sainsburys.model.view.web.ProductView;
import sainsburys.util.exception.ServiceException;

/**
 * Parses a document representing a Web page.
 */
public interface ProductWebPageParser {

  /**
   * Parses the Web page document, producing product views.
   * 
   * @param document
   *   the document to be parsed.
   * @return
   *   the product views.
   * @throws ServiceException
   *   any exception during parsing of the Web page document.
   */
  public List<ProductView> parse(Document document) throws ServiceException;

}
