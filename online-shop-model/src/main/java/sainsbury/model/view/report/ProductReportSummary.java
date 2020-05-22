
package sainsbury.model.view.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Represents a summary of products on a report.
 */
@Data
@JsonPropertyOrder({"gross", "vat"})
@JsonInclude(Include.NON_NULL)
public class ProductReportSummary {

  public static final int SCALE = 2;

  @JsonProperty
  private BigDecimal gross = new BigDecimal(0).setScale(SCALE);

  @JsonProperty
  private BigDecimal vat;

  /**
   * Adds the unit price to the gross amount.
   * 
   * @param unitPrice the unit price.
   */
  public void addToGross(BigDecimal unitPrice) {
    gross = gross.add(unitPrice);
  }

}
