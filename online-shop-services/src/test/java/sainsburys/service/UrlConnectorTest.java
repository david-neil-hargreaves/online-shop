
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
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsburys.test.AbstractTest;
import sainsburys.util.exception.ServiceException;

public class UrlConnectorTest extends AbstractTest {

  @Mock
  private HttpConnection mockHttpConnection;

  @InjectMocks
  private UrlConnector urlConnector = new UrlConnectorImpl();

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
    when(mockHttpConnection.url(url)).thenReturn(mockConnection);
    when(mockConnection.get()).thenReturn(mockDocument);
    urlConnector.getDocument(url);
    verify(mockHttpConnection, times(1)).url(url);
    verify(mockConnection, times(1)).get();
  }

  @Test
  public void testGetDocumentIoException() throws ServiceException, IOException {
    String url = String.format(URL_FORMAT, WEB_PAGE_PROTOCOL, WEB_PAGE_SERVER, WEB_PAGE_RESOURCE);
    when(mockHttpConnection.url(url)).thenReturn(mockConnection);
    IOException ioException = new IOException();
    when(mockConnection.get()).thenThrow(ioException);
    ServiceException serviceException = new ServiceException(ioException);
    expectedException.expect(serviceException.getClass());
    expectedException.expectMessage(serviceException.getMessage());
    expectedException.expectCause(IsEqual.equalTo(ioException));
    urlConnector.getDocument(url);
  }

}
