
package sainsburys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static sainsburys.service.ProductWebPageParserImpl.DESCRIPTION_SELECTOR;
import static sainsburys.service.ProductWebPageParserImpl.LINKS_SELECTOR;
import static sainsburys.service.ProductWebPageParserImpl.MESSAGE_INVALID_NUMBER_OF_ELEMENTS;
import static sainsburys.service.ProductWebPageParserImpl.MESSAGE_MISSING_CALORIE_INFORMATION;
import static sainsburys.service.ProductWebPageParserImpl.NUTRITION_TABLE_SELECTOR;
import static sainsburys.service.ProductWebPageParserImpl.PRICE_PER_UNIT_SELECTOR;
import static sainsburys.service.ProductWebPageParserImpl.PRODUCTS_SELECTOR;
import static sainsburys.test.TestData.DESCRIPTION;
import static sainsburys.test.TestData.DESCRIPTION_2;
import static sainsburys.test.TestData.FULL_HYPERLINK;
import static sainsburys.test.TestData.FULL_HYPERLINK_2;
import static sainsburys.test.TestData.STRING_UNIT_PRICE;
import static sainsburys.test.TestData.STRING_UNIT_PRICE_2;
import static sainsburys.test.TestData.TITLE;
import static sainsburys.test.TestData.createDescriptionDivs;
import static sainsburys.test.TestData.createDivElement;
import static sainsburys.test.TestData.createNutritionTableNoCalories;
import static sainsburys.test.TestData.createNutritionTableOne;
import static sainsburys.test.TestData.createNutritionTableOneCaloriesOnTwoRows;
import static sainsburys.test.TestData.createNutritionTablesTwo;
import static sainsburys.test.TestData.createNutritionTablesZero;
import static sainsburys.test.TestData.createPricePerUnitDivs;
import static sainsburys.test.TestData.createProductDivsOne;
import static sainsburys.test.TestData.createProductDivsTwo;
import static sainsburys.test.TestData.createProductView;
import static sainsburys.test.TestData.createProductView2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.core.IsEqual;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sainsburys.model.view.web.ProductView;
import sainsburys.test.AbstractTest;
import sainsburys.util.exception.ServiceException;

public class ProductWebPageParserTest extends AbstractTest {

  private static final String ATTRIBUTE_PRODUCT_VIEWS = "Product views";

  @Mock
  private HttpConnection mockHttpConnection;

  @Mock
  private UrlConnector mockHyperlinkConnector;

  @InjectMocks
  private ProductWebPageParser productWebPageParser = new ProductWebPageParserImpl();

  @Mock
  private Connection mockConnection;

  @Mock
  private Document mockDocumentProductsPage;

  @Mock
  private Document mockDocumentProductPage;

  @Mock
  private Document mockDocumentProductPage2;

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void testParseSingleProductNoNutritionTable() throws ServiceException, IOException {
    Elements nutritionTables = createNutritionTablesZero();
    ProductView expectedProductView = createProductView();
    expectedProductView.setKilocaloriesPer100Grams(null);
    testParseSingleProduct(nutritionTables, expectedProductView);
  }

  @Test
  public void testParseSingleProductHasNutritionTable() throws ServiceException, IOException {
    Elements nutritionTables = createNutritionTableOne();
    ProductView expectedProductView = createProductView();
    testParseSingleProduct(nutritionTables, expectedProductView);
  }

  @Test
  public void testParseSingleProductHasNutritionTableCaloriesOnTwoRows()
      throws ServiceException, IOException {
    Elements nutritionTables = createNutritionTableOneCaloriesOnTwoRows();
    ProductView expectedProductView = createProductView();
    testParseSingleProduct(nutritionTables, expectedProductView);
  }

  @Test
  public void testMultipleProducts() throws ServiceException {
    // Set up test data.
    Elements productDivs = createProductDivsTwo();
    Elements pricePerUnitDivsProduct1 = createPricePerUnitDivs(STRING_UNIT_PRICE);
    Elements descriptionDivsProduct1 = createDescriptionDivs(DESCRIPTION);
    Elements nutritionTablesProduct1 = createNutritionTableOne();
    Elements pricePerUnitDivsProduct2 = createPricePerUnitDivs(STRING_UNIT_PRICE_2);
    Elements descriptionDivsProduct2 = createDescriptionDivs(DESCRIPTION_2);
    Elements nutritionTablesProduct2 = createNutritionTablesZero();
    // Set up mock test data.
    when(mockDocumentProductsPage.select(PRODUCTS_SELECTOR)).thenReturn(productDivs);
    when(mockHyperlinkConnector.getDocument(FULL_HYPERLINK)).thenReturn(mockDocumentProductPage);
    when(mockDocumentProductPage.select(PRICE_PER_UNIT_SELECTOR))
        .thenReturn(pricePerUnitDivsProduct1);
    when(mockDocumentProductPage.select(DESCRIPTION_SELECTOR)).thenReturn(descriptionDivsProduct1);
    when(mockDocumentProductPage.select(NUTRITION_TABLE_SELECTOR))
        .thenReturn(nutritionTablesProduct1);
    when(mockHyperlinkConnector.getDocument(FULL_HYPERLINK_2)).thenReturn(mockDocumentProductPage2);
    when(mockDocumentProductPage2.select(PRICE_PER_UNIT_SELECTOR))
        .thenReturn(pricePerUnitDivsProduct2);
    when(mockDocumentProductPage2.select(DESCRIPTION_SELECTOR)).thenReturn(descriptionDivsProduct2);
    when(mockDocumentProductPage2.select(NUTRITION_TABLE_SELECTOR))
        .thenReturn(nutritionTablesProduct2);
    // Set up expected results.
    List<ProductView> expectedProductViews = new ArrayList<>();
    ProductView expectedProductView1 = createProductView();
    expectedProductViews.add(expectedProductView1);
    ProductView expectedProductView2 = createProductView2();
    expectedProductViews.add(expectedProductView2);
    expectedProductView2.setKilocaloriesPer100Grams(null);
    // Execute the method under test.
    List<ProductView> actualProductViews = productWebPageParser.parse(mockDocumentProductsPage);
    // Verify results.
    assertEquals(ATTRIBUTE_PRODUCT_VIEWS, expectedProductViews, actualProductViews);
    verify(mockDocumentProductsPage, times(1)).select(PRODUCTS_SELECTOR);
    verify(mockHyperlinkConnector, times(1)).getDocument(FULL_HYPERLINK);
    verify(mockDocumentProductPage, times(1)).select(PRICE_PER_UNIT_SELECTOR);
    verify(mockDocumentProductPage, times(1)).select(DESCRIPTION_SELECTOR);
    verify(mockDocumentProductPage, times(1)).select(NUTRITION_TABLE_SELECTOR);
    verify(mockHyperlinkConnector, times(1)).getDocument(FULL_HYPERLINK_2);
    verify(mockDocumentProductPage2, times(1)).select(PRICE_PER_UNIT_SELECTOR);
    verify(mockDocumentProductPage2, times(1)).select(DESCRIPTION_SELECTOR);
    verify(mockDocumentProductPage2, times(1)).select(NUTRITION_TABLE_SELECTOR);
  }

  @Test
  public void testParseEmptyDocument() throws ServiceException, IOException {
    Elements productDivs = new Elements();
    when(mockDocumentProductsPage.select(PRODUCTS_SELECTOR)).thenReturn(productDivs);
    List<ProductView> expectedProductViews = new ArrayList<>();
    List<ProductView> actualProductViews = productWebPageParser.parse(mockDocumentProductsPage);
    assertEquals(ATTRIBUTE_PRODUCT_VIEWS, expectedProductViews, actualProductViews);
    verify(mockDocumentProductsPage, times(1)).select(PRODUCTS_SELECTOR);
  }

  @Test
  public void testParseNoHref() throws ServiceException, IOException {
    Elements productDivs = new Elements();
    Element productDiv = createDivElement();
    productDiv.appendText(TITLE);
    productDivs.add(productDiv);
    when(mockDocumentProductsPage.select(PRODUCTS_SELECTOR)).thenReturn(productDivs);
    String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, TITLE,
        LINKS_SELECTOR, 0);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  @Test
  public void testParseMissingPricePerUnit() throws ServiceException, IOException {
    int numberOfDivs = 0;
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs(numberOfDivs);
    Elements descriptionDivs = createDescriptionDivs();
    Elements nutritionTables = createNutritionTablesZero();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, TITLE,
        PRICE_PER_UNIT_SELECTOR, numberOfDivs);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  @Test
  public void testParseMultiplePricesPerUnit() throws ServiceException, IOException {
    int numberOfDivs = 2;
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs(numberOfDivs);
    Elements descriptionDivs = createDescriptionDivs();
    Elements nutritionTables = createNutritionTablesZero();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, TITLE,
        PRICE_PER_UNIT_SELECTOR, numberOfDivs);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  @Test
  public void testParseMissingDescription() throws ServiceException, IOException {
    int numberOfDivs = 0;
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs();
    Elements descriptionDivs = createDescriptionDivs(numberOfDivs);
    Elements nutritionTables = createNutritionTablesZero();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, TITLE,
        DESCRIPTION_SELECTOR, numberOfDivs);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  @Test
  public void testParseMultipleDescriptions() throws ServiceException, IOException {
    int numberOfDivs = 2;
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs();
    Elements descriptionDivs = createDescriptionDivs(numberOfDivs);
    Elements nutritionTables = createNutritionTablesZero();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, TITLE,
        DESCRIPTION_SELECTOR, numberOfDivs);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  @Test
  public void testParseMultipleNutritionTables() throws ServiceException, IOException {
    int numberOfDivs = 2;
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs();
    Elements descriptionDivs = createDescriptionDivs();
    Elements nutritionTables = createNutritionTablesTwo();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, TITLE,
        NUTRITION_TABLE_SELECTOR, numberOfDivs);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  @Test
  public void testParseNutritionTableNoCalories() throws ServiceException, IOException {
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs();
    Elements descriptionDivs = createDescriptionDivs();
    Elements nutritionTables = createNutritionTableNoCalories();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    String message = String.format(MESSAGE_MISSING_CALORIE_INFORMATION, TITLE);
    ServiceException serviceException = new ServiceException(message);
    testParseExceptionExpected(serviceException);
  }

  private void testParseSingleProduct(Elements nutritionTables, ProductView expectedProductView)
      throws ServiceException {
    // Set up test data.
    Elements productDivs = createProductDivsOne();
    Elements pricePerUnitDivs = createPricePerUnitDivs();
    Elements descriptionDivs = createDescriptionDivs();
    setUpMockData(productDivs, pricePerUnitDivs, descriptionDivs, nutritionTables);
    // Set up expected results.
    List<ProductView> expectedProductViews = new ArrayList<>();
    expectedProductViews.add(expectedProductView);
    // Execute the method under test.
    List<ProductView> actualProductViews = productWebPageParser.parse(mockDocumentProductsPage);
    // Verify results.
    assertEquals(ATTRIBUTE_PRODUCT_VIEWS, expectedProductViews, actualProductViews);
    verify(mockDocumentProductsPage, times(1)).select(PRODUCTS_SELECTOR);
    verify(mockHyperlinkConnector, times(1)).getDocument(FULL_HYPERLINK);
    verify(mockDocumentProductPage, times(1)).select(PRICE_PER_UNIT_SELECTOR);
    verify(mockDocumentProductPage, times(1)).select(DESCRIPTION_SELECTOR);
    verify(mockDocumentProductPage, times(1)).select(NUTRITION_TABLE_SELECTOR);
  }

  private void setUpMockData(Elements productDivs, Elements pricePerUnitDivs,
      Elements descriptionDivs, Elements nutritionTables) throws ServiceException {
    when(mockDocumentProductsPage.select(PRODUCTS_SELECTOR)).thenReturn(productDivs);
    when(mockHyperlinkConnector.getDocument(FULL_HYPERLINK)).thenReturn(mockDocumentProductPage);
    when(mockDocumentProductPage.select(PRICE_PER_UNIT_SELECTOR)).thenReturn(pricePerUnitDivs);
    when(mockDocumentProductPage.select(DESCRIPTION_SELECTOR)).thenReturn(descriptionDivs);
    when(mockDocumentProductPage.select(NUTRITION_TABLE_SELECTOR)).thenReturn(nutritionTables);
  }

  private void testParseExceptionExpected(ServiceException serviceException)
      throws ServiceException {
    expectedException.expect(serviceException.getClass());
    expectedException.expectMessage(serviceException.getMessage());
    expectedException.expectCause(IsEqual.equalTo(null));
    productWebPageParser.parse(mockDocumentProductsPage);
  }

}
