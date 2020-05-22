
package sainsburys.service;

import org.jsoup.nodes.Document;
import sainsburys.util.exception.ServiceException;

/**
 * Connects to a Web page, providing a method to obtain a document representing the page.
 */
public interface UrlConnector {

  /**
   * Connects to a Web page obtaining a document representing the page.
   * 
   * @param url
   *   the URL.
   * @return
   *   the document representing the Web page.
   * @throws ServiceException
   *   any exception whilst connecting to the Web page and obtaining it's document representation.
   */
  public Document getDocument(String url) throws ServiceException;

}
