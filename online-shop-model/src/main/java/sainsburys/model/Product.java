
package sainsburys.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.Data;
import sainsburys.util.StringUtil;

/**
 * Represents a product.
 */
@Data
@JsonPropertyOrder({"title", "kcal_per_100g", "unit_price", "description"})
@JsonInclude(Include.NON_NULL)
public class Product implements Comparable<Product> {

  @JsonProperty
  private String title;

  @JsonProperty(value = "kcal_per_100g")
  private Integer kilocaloriesPer100Grams;

  @JsonProperty(value = "unit_price")
  private BigDecimal unitPrice;

  @JsonIgnore
  private String description;

  @JsonIgnore
  private Integer sortOrderNumber;

  /**
   * Returns the first line of the description.
   * 
   * @return the first line of the description.
   */
  @JsonGetter(value = "description")
  public String getDescriptionFirstLine() {
    return StringUtil.getFirstLine(description);
  }

  /**
   * Compares two products for use when sorting products in a logical sort order. The sort order
   * number is used for sorting a product. The sort order number is derived from the order of
   * products shown in the Web page. A product with a lower sort order number is deemed to appear
   * before one with a higher sort order number.
   * 
   * @param other the other product for use in the comparison.
   * @return
   *         <li>-1 if the other product has a higher sort order number.
   *         <li>1 if the other product has a lower sort order number.
   *         <li>0 if the other product has an equal sort order number.
   */
  @Override
  public int compareTo(Product other) {
    return this.sortOrderNumber.compareTo(other.sortOrderNumber);
  }

}
