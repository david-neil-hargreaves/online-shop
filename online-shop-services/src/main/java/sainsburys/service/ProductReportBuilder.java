
package sainsburys.service;

import sainsburys.util.exception.ServiceException;

/**
 * Builds a product report. Details are implementation-specific.
 * 
 * @see ProductReportBuilderImpl
 */
public interface ProductReportBuilder {

  /**
   * Builds a product report.
   * 
   * @throws ServiceException
   *   any exception during building of the product report.
   */
  public void perform() throws ServiceException;

}
