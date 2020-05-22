
package sainsburys.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sainsbury.model.view.report.ProductReportContents;
import sainsbury.model.view.report.ProductReportSummary;
import sainsburys.model.Product;
import sainsburys.model.view.web.ProductView;

public class TestData {

  public static final String TITLE = "Sainsbury's Strawberries 400g";
  public static final String TITLE_2 = "Sainsbury's Cherry Punnet 200g";
  public static final String STRING_KILOCALORIES_PER_100_GRAMS = "33";
  public static final Integer KILOCALORIES_PER_100_GRAMS =
      new Integer(STRING_KILOCALORIES_PER_100_GRAMS);
  public static final String STRING_KILOCALORIES_PER_100_GRAMS_2 = "52";
  public static final Integer KILOCALORIES_PER_100_GRAMS_2 =
      new Integer(STRING_KILOCALORIES_PER_100_GRAMS_2);
  public static final String STRING_UNIT_PRICE = "1.75";
  public static final BigDecimal UNIT_PRICE = new BigDecimal(STRING_UNIT_PRICE);
  public static final String STRING_UNIT_PRICE_2 = "1.50";
  public static final BigDecimal UNIT_PRICE_2 = new BigDecimal(STRING_UNIT_PRICE_2);
  public static final String DESCRIPTION = "by Sainsbury's strawberries";
  public static final String DESCRIPTION_2 = "Cherries";
  public static final Integer SORT_ORDER_NUMBER = new Integer(1);
  public static final Integer SORT_ORDER_NUMBER_2 = new Integer(2);
  public static final BigDecimal GROSS = new BigDecimal("3.25");
  public static final BigDecimal VAT = new BigDecimal("0.65");
  public static final String SERVICE_EXCEPTION_MESSAGE = "Service exception message";
  public static final String FORMATTED_PRODUCT_REPORT = "Formatted product report";
  public static final String WEB_PAGE_PROTOCOL = "https";
  public static final String WEB_PAGE_SERVER = "server";
  public static final String WEB_PAGE_RESOURCE = "resource";
  public static final String DIV_TAG = "div";
  public static final String A_TAG = "a";
  public static final String H3_TAG = "h3";
  public static final String TABLE_TAG = "table";
  public static final String ATTRIBUTE_HREF = "href";
  public static final String BASE_URI = "https://server/resource";
  public static final String HYPERLINK = "hyperlink";
  public static final String HYPERLINK_2 = "hyperlink2";
  public static final String FULL_HYPERLINK = "https://server/hyperlink";
  public static final String FULL_HYPERLINK_2 = "https://server/hyperlink2";
  public static final String NUTRITION_TABLE_BODY =
      "<tbody><tr><td>Energy kcal</td><td>33</td></tr></tbody>";
  public static final String NUTRITION_TABLE_BODY_CALORIES_ON_TWO_ROWS =
      "<tbody><tr><td>Energy</td></tr><tr><td>33<td></tr></tbody>";
  public static final String NUTRITION_TABLE_BODY_NO_CALORIES =
      "<tbody><tr><td>Grams</td><td>66</td></tr></tbody>";
  public static final int ZERO = 0;
  public static final int ONE = 1;
  public static final int TWO = 2;

  public static ProductView createProductView() {
    ProductView productView = new ProductView(TITLE);
    productView.setKilocaloriesPer100Grams(STRING_KILOCALORIES_PER_100_GRAMS);
    productView.setUnitPrice(STRING_UNIT_PRICE);
    productView.setDescription(DESCRIPTION);
    productView.setSortOrderNumber(SORT_ORDER_NUMBER);
    return productView;
  }

  public static ProductView createProductView2() {
    ProductView productView = new ProductView(TITLE_2);
    productView.setKilocaloriesPer100Grams(STRING_KILOCALORIES_PER_100_GRAMS_2);
    productView.setUnitPrice(STRING_UNIT_PRICE_2);
    productView.setDescription(DESCRIPTION_2);
    productView.setSortOrderNumber(SORT_ORDER_NUMBER_2);
    return productView;
  }

  public static Product createProduct() {
    Product product = new Product();
    product.setTitle(TITLE);
    product.setKilocaloriesPer100Grams(KILOCALORIES_PER_100_GRAMS);
    product.setUnitPrice(UNIT_PRICE);
    product.setDescription(DESCRIPTION);
    product.setSortOrderNumber(SORT_ORDER_NUMBER);
    return product;
  }

  public static Product createProduct2() {
    Product product = new Product();
    product.setTitle(TITLE_2);
    product.setKilocaloriesPer100Grams(KILOCALORIES_PER_100_GRAMS_2);
    product.setUnitPrice(UNIT_PRICE_2);
    product.setDescription(DESCRIPTION_2);
    product.setSortOrderNumber(SORT_ORDER_NUMBER_2);
    return product;
  }

  public static List<ProductView> createProductViews() {
    List<ProductView> productViews = new ArrayList<>();
    productViews.add(createProductView());
    productViews.add(createProductView2());
    return productViews;
  }

  public static List<Product> createProducts() {
    List<Product> products = new ArrayList<>();
    products.add(createProduct());
    products.add(createProduct2());
    return products;
  }

  public static ProductReportSummary createProductReportSummary() {
    ProductReportSummary productReportSummary = new ProductReportSummary();
    productReportSummary.setGross(GROSS);
    productReportSummary.setVat(VAT);
    return productReportSummary;
  }

  public static ProductReportContents createProductReportContents() {
    List<Product> products = createProducts();
    ProductReportSummary productReportSummary = createProductReportSummary();
    return new ProductReportContents(products, productReportSummary);
  }

  public static Element createDivElement() {
    return new Element(DIV_TAG);
  }

  public static Element createTableElement() {
    return new Element(TABLE_TAG);
  }

  public static Elements createProductDivsOne() {
    Elements productDivs = new Elements();
    Element productDiv = createDivElement();
    productDivs.add(productDiv);
    productDiv.setBaseUri(BASE_URI);
    productDiv.appendText(TITLE);
    productDiv.appendElement(A_TAG).attr(ATTRIBUTE_HREF, HYPERLINK);
    return productDivs;
  }

  public static Elements createProductDivsTwo() {
    Elements productDivs = new Elements();
    Element productDiv1 = createDivElement();
    productDivs.add(productDiv1);
    productDiv1.setBaseUri(BASE_URI);
    productDiv1.appendText(TITLE);
    productDiv1.appendElement(A_TAG).attr(ATTRIBUTE_HREF, HYPERLINK);
    Element productDiv2 = createDivElement();
    productDivs.add(productDiv2);
    productDiv2.setBaseUri(BASE_URI);
    productDiv2.appendText(TITLE_2);
    productDiv2.appendElement(A_TAG).attr(ATTRIBUTE_HREF, HYPERLINK_2);
    return productDivs;
  }

  public static Elements createPricePerUnitDivs(String unitPrice, int numberOfDivs) {
    Elements pricePerUnitDivs = new Elements();
    for (int i = 1; i <= numberOfDivs; i++) {
      Element pricePerUnitDiv = createDivElement();
      pricePerUnitDivs.add(pricePerUnitDiv);
      pricePerUnitDiv.appendText(unitPrice);
    }
    return pricePerUnitDivs;
  }
  
  public static Elements createPricePerUnitDivs() {
    return createPricePerUnitDivs(STRING_UNIT_PRICE, ONE);
  }
  
  public static Elements createPricePerUnitDivs(String unitPrice) {
    return createPricePerUnitDivs(unitPrice, ONE);
  }
  
  public static Elements createPricePerUnitDivs(int numberOfDivs) {
    return createPricePerUnitDivs(STRING_UNIT_PRICE, numberOfDivs);
  }

  public static Elements createDescriptionDivs(String description, int numberOfDivs) {
    Elements descriptionDivs = new Elements();
    for (int i = 1; i <= numberOfDivs; i++) {
      Element descriptionDiv = createDivElement();
      descriptionDivs.add(descriptionDiv);
      descriptionDiv.appendElement(H3_TAG).appendText(description);
    }
    return descriptionDivs;
  }

  public static Elements createDescriptionDivs() {
    return createDescriptionDivs(DESCRIPTION, ONE);
  }

  public static Elements createDescriptionDivs(String description) {
    return createDescriptionDivs(description, ONE);
  }

  public static Elements createDescriptionDivs(int numberOfDivs) {
    return createDescriptionDivs(DESCRIPTION, numberOfDivs);
  }

  public static Elements createNutritionTablesZero() {
    Elements nutritionTables = new Elements();
    return nutritionTables;
  }

  public static Elements createNutritionTableOne() {
    Elements nutritionTables = new Elements();
    Element nutritionTable = createTableElement();
    nutritionTables.add(nutritionTable);
    nutritionTable.append(NUTRITION_TABLE_BODY);
    return nutritionTables;
  }

  public static Elements createNutritionTablesTwo() {
    Elements nutritionTables = new Elements();
    int numberOfDivs = 2;
    for (int i = 1; i <= numberOfDivs; i++) {
      Element nutritionTable = createTableElement();
      nutritionTables.add(nutritionTable);
      nutritionTable.append(NUTRITION_TABLE_BODY);
    }
    return nutritionTables;
  }

  public static Elements createNutritionTableOneCaloriesOnTwoRows() {
    Elements nutritionTables = new Elements();
    Element nutritionTable = createTableElement();
    nutritionTables.add(nutritionTable);
    nutritionTable.append(NUTRITION_TABLE_BODY_CALORIES_ON_TWO_ROWS);
    return nutritionTables;
  }

  public static Elements createNutritionTableNoCalories() {
    Elements nutritionTables = new Elements();
    Element nutritionTable = createTableElement();
    nutritionTables.add(nutritionTable);
    nutritionTable.append(NUTRITION_TABLE_BODY_NO_CALORIES);
    return nutritionTables;
  }

}
