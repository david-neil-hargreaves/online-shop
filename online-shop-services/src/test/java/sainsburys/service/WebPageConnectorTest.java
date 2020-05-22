
package sainsburys.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.service.WebPageConnectorImpl.URL_FORMAT;
import static sainsburys.test.TestData.WEB_PAGE_PROTOCOL;
import static sainsburys.test.TestData.WEB_PAGE_RESOURCE;
import static sainsburys.test.TestData.WEB_PAGE_SERVER;

import java.io.IOException;
import org.hamcrest.core.IsEqual;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsburys.test.AbstractTest;
import sainsburys.util.exception.ServiceException;

public class WebPageConnectorTest extends AbstractTest {

  @Mock
  private UrlConnector mockUrlConnector;

  @InjectMocks
  private WebPageConnector webPageConnector = new WebPageConnectorImpl();
  
  {
    ((WebPageConnectorImpl) webPageConnector).setWebPageProtocol(WEB_PAGE_PROTOCOL);
    ((WebPageConnectorImpl) webPageConnector).setWebPageServer(WEB_PAGE_SERVER);
    ((WebPageConnectorImpl) webPageConnector).setWebPageResource(WEB_PAGE_RESOURCE);
    ((WebPageConnectorImpl) webPageConnector).setUp();
  }

  @Mock
  private Connection mockConnection;

  @Mock
  private Document mockDocument;

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void testGetDocument() throws ServiceException, IOException {
    String url = String.format(URL_FORMAT, WEB_PAGE_PROTOCOL, WEB_PAGE_SERVER, WEB_PAGE_RESOURCE);
    when(mockUrlConnector.getDocument(url)).thenReturn(mockDocument);
    webPageConnector.getDocument();
    verify(mockUrlConnector, times(1)).getDocument(url);
  }

  @Test
  public void testGetDocumentIoException() throws ServiceException, IOException {
    String url = String.format(URL_FORMAT, WEB_PAGE_PROTOCOL, WEB_PAGE_SERVER, WEB_PAGE_RESOURCE);
    IOException ioException = new IOException();
    ServiceException serviceException = new ServiceException(ioException);
    when(mockUrlConnector.getDocument(url)).thenThrow(serviceException);
    expectedException.expect(serviceException.getClass());
    expectedException.expectMessage(serviceException.getMessage());
    expectedException.expectCause(IsEqual.equalTo(ioException));
    webPageConnector.getDocument();
  }

}
