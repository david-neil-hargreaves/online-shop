
package sainsburys.service;

import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sainsburys.util.exception.ServiceException;

@Service
public class WebPageConnectorImpl implements WebPageConnector {

  private static final Logger LOGGER = LogManager.getLogger(WebPageConnectorImpl.class);

  public static final String URL_FORMAT = "%s://%s/%s";

  @Autowired
  @Value("${web-page-protocol}")
  private String webPageProtocol;

  @Autowired
  @Value("${web-page-server}")
  private String webPageServer;

  @Autowired
  @Value("${web-page-resource}")
  private String webPageResource;

  private String url;

  @Autowired
  private UrlConnector urlConnector;

  @PostConstruct
  public void setUp() {
    url = String.format(URL_FORMAT, webPageProtocol, webPageServer, webPageResource);
  }

  @Override
  public Document getDocument() throws ServiceException {
    return LOGGER.traceExit(urlConnector.getDocument(url));
  }

  void setWebPageProtocol(String webPageProtocol) {
    this.webPageProtocol = webPageProtocol;
  }

  void setWebPageServer(String webPageServer) {
    this.webPageServer = webPageServer;
  }

  void setWebPageResource(String webPageResource) {
    this.webPageResource = webPageResource;
  }

}
