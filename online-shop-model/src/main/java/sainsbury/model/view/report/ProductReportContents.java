
package sainsbury.model.view.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import sainsburys.model.Product;

/**
 * Represents a report of products.
 */
@Data
@AllArgsConstructor
@JsonPropertyOrder({"results", "total"})
@JsonInclude(Include.NON_NULL)
public class ProductReportContents {

  @JsonProperty(value = "results")
  private List<Product> products;

  @JsonProperty(value = "total")
  private ProductReportSummary productSummary;

}
