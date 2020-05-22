
package sainsburys.service;

import org.jsoup.nodes.Document;
import sainsburys.util.exception.ServiceException;

/**
 * Connects to a Web page, providing a method to obtain a document representing the page.
 */
public interface WebPageConnector {

  /**
   * Connects to a Web page obtaining a document representing the page.
   * 
   * @return
   *   the document representing the Web page.
   * @throws ServiceException
   *   any exception whilst connecting to the Web page and obtaining it's document representation.
   */
  public Document getDocument() throws ServiceException;

}
