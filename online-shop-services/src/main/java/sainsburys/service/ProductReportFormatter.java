
package sainsburys.service;

import sainsbury.model.view.report.ProductReportContents;
import sainsburys.util.exception.ServiceException;

/**
 * Formats a product report.
 */
public interface ProductReportFormatter {

  /**
   * Formats a product report from the given report contents.
   * 
   * @param productReportContents
   *   the product report contents.
   * @return
   *   the formatted product report.
   * @throws ServiceException
   *   any exception during formatting of the product report.
   */
  public String formatReport(ProductReportContents productReportContents) throws ServiceException;
}
