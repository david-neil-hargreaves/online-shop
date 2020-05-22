
package sainsburys.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sainsburys.model.view.web.ProductView;
import sainsburys.util.exception.ServiceException;

@Service
public class ProductWebPageParserImpl implements ProductWebPageParser {

  private static final Logger LOGGER = LogManager.getLogger(ProductWebPageParserImpl.class);

  public static final String PRODUCTS_SELECTOR = "div.productNameAndPromotions";

  public static final String LINKS_SELECTOR = "a[href]";

  // The > operator gets a child element.
  public static final String PRICE_PER_UNIT_SELECTOR =
      "div.pricingAndTrolleyOptions > div.priceTab > div.pricing > p.pricePerUnit";

  // The + operator gets a sibling element.
  public static final String DESCRIPTION_SELECTOR =
      "h3:contains(Description) + div";

  public static final String NUTRITION_TABLE_SELECTOR =
      "table.nutritionTable";

  public static final String CALORIES_ROW_SELECTOR = "tbody > tr";

  public static final String CALORIE_ROW_LABEL = "Energy kcal";

  public static final String CALORIE_ROW_LABEL_CALORIES_ON_TWO_ROWS = "Energy";

  public static final String HREF = "href";

  public static final String MESSAGE_INVALID_NUMBER_OF_ELEMENTS =
      "Expecting a single element for product %s with selector %s, but found %s.";

  public static final String MESSAGE_NUMBER_OF_PRODUCTS =
      "Number of products is %s.";

  public static final String MESSAGE_MISSING_CALORIE_INFORMATION =
      "Missing calorie information for product %s.";

  @Autowired
  private UrlConnector urlConnector;

  @Override
  public List<ProductView> parse(Document document) throws ServiceException {
    LOGGER.traceEntry(document.toString());
    return LOGGER.traceExit(parseProducts(document));
  }

  private List<ProductView> parseProducts(Document document) throws ServiceException {
    List<ProductView> products = new ArrayList<>();
    Elements productDivs = document.select(PRODUCTS_SELECTOR);
    LOGGER.debug(String.format(MESSAGE_NUMBER_OF_PRODUCTS, productDivs.size()));
    int sortOrderNumber = 0;
    for (Element productDiv : productDivs) {
      ProductView product = new ProductView(productDiv.text());
      product.setSortOrderNumber(++sortOrderNumber);
      products.add(product);
      String elementSelector = LINKS_SELECTOR;
      Elements links = productDiv.select(elementSelector);
      validateSingleElement(links, product, elementSelector);
      populateProductDetails(product, links.get(0).absUrl(HREF));
    }
    return products;
  }

  private void populateProductDetails(ProductView product, String hyperlink)
      throws ServiceException {
    Document document = urlConnector.getDocument(hyperlink);
    populateUnitPrice(product, document);
    populateDescription(product, document);
    populateCalories(product, document);
  }

  private void populateUnitPrice(ProductView product, Document document) throws ServiceException {
    String elementSelector = PRICE_PER_UNIT_SELECTOR;
    Elements elements = document.select(elementSelector);
    validateSingleElement(elements, product, elementSelector);
    product.setUnitPrice(elements.get(0).ownText());
  }

  private void populateDescription(ProductView product, Document document) throws ServiceException {
    String elementSelector = DESCRIPTION_SELECTOR;
    Elements elements = document.select(elementSelector);
    validateSingleElement(elements, product, elementSelector);
    product.setDescription(elements.get(0).text());
  }

  private void populateCalories(ProductView product, Document document) throws ServiceException {
    String elementSelector = NUTRITION_TABLE_SELECTOR;
    Elements nutritionTables = document.select(elementSelector);
    if (nutritionTables.size() > 1) {
      String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, product.getTitle(),
          elementSelector, nutritionTables.size());
      throw new ServiceException(message);
    }
    // Nutrition table is optional.
    if (nutritionTables.size() == 1) {
      populateCalories(product, nutritionTables.get(0));
    }
  }

  private void populateCalories(ProductView product, Element nutritionTableElement)
      throws ServiceException {
    Elements tableRows = nutritionTableElement.select(CALORIES_ROW_SELECTOR);
    boolean caloriesFound = false;
    boolean caloriesOnNextRow = false;
    for (Element tableRow : tableRows) {
      Elements tableCells = tableRow.children();
      String rowLabel = tableCells.get(0).text();
      if (caloriesOnNextRow || rowLabel.equals(CALORIE_ROW_LABEL)) {
        caloriesFound = true;
        int calorieColumnNumber = 1;
        if (caloriesOnNextRow) {
          calorieColumnNumber = 0;
        }
        product.setKilocaloriesPer100Grams(tableCells.get(calorieColumnNumber).text());
        break;
      } else
        if (rowLabel.equals(CALORIE_ROW_LABEL_CALORIES_ON_TWO_ROWS)) {
          caloriesOnNextRow = true;
        }
    }
    if (!caloriesFound) {
      String message = String.format(MESSAGE_MISSING_CALORIE_INFORMATION, product.getTitle());
      throw new ServiceException(message);
    }
  }

  private void validateSingleElement(Elements elements, ProductView product, String elementSelector)
      throws ServiceException {
    if ((elements.size() == 0) || (elements.size() > 1)) {
      String message = String.format(MESSAGE_INVALID_NUMBER_OF_ELEMENTS, product.getTitle(),
          elementSelector, elements.size());
      throw new ServiceException(message);
    }
  }

}
