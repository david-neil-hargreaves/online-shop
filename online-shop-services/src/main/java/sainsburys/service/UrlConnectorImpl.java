
package sainsburys.service;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import sainsburys.util.exception.ServiceException;

@Service
public class UrlConnectorImpl implements UrlConnector {

  private static final Logger LOGGER = LogManager.getLogger(UrlConnectorImpl.class);

  private HttpConnection httpConnection = new HttpConnection();

  @Override
  public Document getDocument(String url) throws ServiceException {
    LOGGER.traceEntry();
    Document document;
    try {
      document = httpConnection.url(url).get();
    } catch (IOException exception) {
      LOGGER.error(exception.getMessage(), exception);
      throw new ServiceException(exception);
    }
    return LOGGER.traceExit(document);
  }

}
